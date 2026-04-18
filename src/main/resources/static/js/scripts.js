const currentUser = document.body.dataset.currentUser || '';

function openModal(id) {
  document.getElementById('modal-' + id).classList.remove('hidden');
  document.body.style.overflow = 'hidden';
  loadReviews(id);
}

function closeModal(id) {
  document.getElementById('modal-' + id).classList.add('hidden');
  document.body.style.overflow = '';
}

function loadReviews(movieId) {
  const list = document.getElementById('reviews-list-' + movieId);
  if (!list) return;
  fetch('/api/reviews/movie/' + movieId)
    .then(r => r.json())
    .then(reviews => renderReviews(list, reviews, movieId))
    .catch(() => { list.innerHTML = '<p class="reviews-empty">Could not load reviews.</p>'; });
}

function renderReviews(list, reviews, movieId) {
  if (reviews.length === 0) {
    list.innerHTML = '<p class="reviews-empty">No reviews yet. Be the first!</p>';
    return;
  }
  list.innerHTML = reviews.map(r => {
    const isOwner = currentUser && r.userName === currentUser;
    return `
      <div class="review-item"
           data-review-id="${r.id}"
           data-grade="${r.grade}"
           data-comment="${escapeAttr(r.comment || '')}">
        <div class="review-item-header">
          <span class="review-author">${escapeHtml(r.userName)}</span>
          <div class="review-header-right">
            <span class="review-stars">${starsHtml(r.grade)}</span>
            ${isOwner ? `<button class="review-edit-btn" data-movie-id="${movieId}">✏️</button>` : ''}
          </div>
        </div>
        ${r.comment ? `<p class="review-comment">${escapeHtml(r.comment)}</p>` : ''}
      </div>`;
  }).join('');

  list.querySelectorAll('.review-edit-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const item = btn.closest('.review-item');
      showEditForm(item, parseInt(btn.dataset.movieId));
    });
  });
}

function showEditForm(item, movieId) {
  const reviewId = parseInt(item.dataset.reviewId);
  const grade = parseFloat(item.dataset.grade);
  const comment = item.dataset.comment;
  const selectedStars = Math.round(grade);

  item.innerHTML = `
    <div class="star-picker-edit" data-selected="${selectedStars}">
      ${[1,2,3,4,5].map(i =>
        `<span class="star ${i <= selectedStars ? 'star-active' : ''}" data-value="${i}">★</span>`
      ).join('')}
    </div>
    <textarea class="review-textarea review-edit-textarea">${escapeHtml(comment)}</textarea>
    <div class="review-edit-actions">
      <button class="btn-secondary review-cancel-btn">Cancel</button>
      <button class="btn-primary review-save-btn">Save</button>
    </div>`;

  wirePicker(item.querySelector('.star-picker-edit'));

  item.querySelector('.review-cancel-btn').addEventListener('click', () => loadReviews(movieId));

  item.querySelector('.review-save-btn').addEventListener('click', () => {
    const picker = item.querySelector('.star-picker-edit');
    const newGrade = parseInt(picker.dataset.selected);
    const newComment = item.querySelector('.review-edit-textarea').value.trim();
    if (newGrade === 0) { alert('Please select a rating.'); return; }

    fetch('/api/reviews/' + reviewId, {
      method: 'PATCH',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({grade: newGrade, comment: newComment})
    })
    .then(r => { if (!r.ok) throw new Error(); return r.json(); })
    .then(() => loadReviews(movieId))
    .catch(() => alert('Error saving review. Please try again.'));
  });
}

function wirePicker(picker) {
  const stars = picker.querySelectorAll('.star');
  stars.forEach(star => {
    star.addEventListener('mouseenter', () => {
      const val = parseInt(star.dataset.value);
      stars.forEach(s => s.classList.toggle('star-hover', parseInt(s.dataset.value) <= val));
    });
    star.addEventListener('mouseleave', () => stars.forEach(s => s.classList.remove('star-hover')));
    star.addEventListener('click', () => {
      const val = parseInt(star.dataset.value);
      picker.dataset.selected = val;
      stars.forEach(s => s.classList.toggle('star-active', parseInt(s.dataset.value) <= val));
    });
  });
}

function starsHtml(grade) {
  const full = Math.round(grade);
  return Array.from({length: 5}, (_, i) =>
    `<span class="star-display ${i < full ? 'star-filled' : ''}">★</span>`
  ).join('');
}

function escapeHtml(str) {
  return String(str).replace(/[&<>"']/g, c =>
    ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
}

function escapeAttr(str) {
  return String(str).replace(/"/g, '&quot;');
}

document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.movie-card[data-modal]').forEach(card => {
    card.addEventListener('click', () => openModal(card.dataset.modal));
  });

  document.querySelectorAll('.modal-close[data-close]').forEach(btn => {
    btn.addEventListener('click', (e) => {
      e.stopPropagation();
      closeModal(btn.dataset.close);
    });
  });

  document.querySelectorAll('.modal-overlay[data-modal-id]').forEach(overlay => {
    overlay.addEventListener('click', (e) => {
      if (e.target === overlay) closeModal(overlay.dataset.modalId);
    });
  });

  // Wire the new-review star pickers
  document.querySelectorAll('.star-picker').forEach(wirePicker);

  document.querySelectorAll('.review-submit-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const form = btn.closest('.review-form');
      const movieId = parseInt(btn.closest('.modal-overlay').dataset.modalId);
      const picker = form.querySelector('.star-picker');
      const grade = parseInt(picker.dataset.selected);
      const comment = form.querySelector('.review-textarea').value.trim();

      if (grade === 0) {
        alert('Please select a star rating before submitting.');
        return;
      }

      fetch('/api/reviews/movie', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({movieApiId: movieId, grade, comment})
      })
      .then(r => {
        if (r.status === 401) { window.location.href = '/login'; return null; }
        return r.json();
      })
      .then(data => {
        if (!data) return;
        form.querySelector('.review-textarea').value = '';
        picker.dataset.selected = '0';
        picker.querySelectorAll('.star').forEach(s => s.classList.remove('star-active'));
        loadReviews(movieId);
      })
      .catch(() => alert('Error submitting review. Please try again.'));
    });
  });
});
