function openModal(id) {
  document.getElementById('modal-' + id).classList.remove('hidden');
  document.body.style.overflow = 'hidden';
}

function closeModal(id) {
  document.getElementById('modal-' + id).classList.add('hidden');
  document.body.style.overflow = '';
}

document.addEventListener('DOMContentLoaded', () => {
  // Open modal on card click
  document.querySelectorAll('.movie-card[data-modal]').forEach(card => {
    card.addEventListener('click', () => openModal(card.dataset.modal));
  });

  // Close modal on X button click
  document.querySelectorAll('.modal-close[data-close]').forEach(btn => {
    btn.addEventListener('click', (e) => {
      e.stopPropagation();
      closeModal(btn.dataset.close);
    });
  });

  // Close modal on overlay click
  document.querySelectorAll('.modal-overlay[data-modal-id]').forEach(overlay => {
    overlay.addEventListener('click', (e) => {
      if (e.target === overlay) closeModal(overlay.dataset.modalId);
    });
  });
});
