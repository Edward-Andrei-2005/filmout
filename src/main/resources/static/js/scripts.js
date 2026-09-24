function openModal(id) {
    document.getElementById('modal-' + id).classList.remove('hidden');
    document.body.style.overflow = 'hidden';
}

function closeModal(id) {
    document.getElementById('modal-' + id).classList.add('hidden');
    document.body.style.overflow = '';
}

function wirePicker(picker) {
    const stars = picker.querySelectorAll('.star');
    const initial = parseInt(picker.dataset.selected) || 0;
    stars.forEach(s => s.classList.toggle('star-active', parseInt(s.dataset.value) <= initial));

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
            const gradeInput = picker.closest('form')?.querySelector('input[name="grade"]');
            if (gradeInput) gradeInput.value = val;
        });
    });
}

document.addEventListener('DOMContentLoaded', () => {

    // Reopen modal after form submit
    const openParam = new URLSearchParams(window.location.search).get('openModal');
    if (openParam) {
        openModal(openParam);
        history.replaceState({}, '', window.location.pathname);
    }

    // Open modal on card click
    document.querySelectorAll('.movie-card[data-modal]').forEach(card => {
        card.addEventListener('click', () => openModal(card.dataset.modal));
    });

    // Close modal on X button
    document.querySelectorAll('.modal-close[data-close]').forEach(btn => {
        btn.addEventListener('click', e => {
            e.stopPropagation();
            closeModal(btn.dataset.close);
        });
    });

    // Close modal on overlay click
    document.querySelectorAll('.modal-overlay[data-modal-id]').forEach(overlay => {
        overlay.addEventListener('click', e => {
            if (e.target === overlay) closeModal(overlay.dataset.modalId);
        });
    });

    // Close active modal on Escape key press
    document.addEventListener('keydown', e => {
        if (e.key === 'Escape') {
            const activeModal = document.querySelector('.modal-overlay:not(.hidden)');
            if (activeModal) closeModal(activeModal.dataset.modalId);
        }
    });

    // Wire all star pickers
    document.querySelectorAll('.star-picker').forEach(wirePicker);

    // Validate grade before submitting new review
    document.querySelectorAll('.review-submit-btn').forEach(btn => {
        btn.addEventListener('click', e => {
            const form = btn.closest('form');
            const grade = parseInt(form.querySelector('input[name="grade"]').value);
            if (grade === 0) {
                e.preventDefault();
                alert('Please select a star rating before submitting.');
            }
        });
    });

    // Toggle inline edit form
    document.querySelectorAll('.review-edit-toggle').forEach(btn => {
        btn.addEventListener('click', () => {
            const item = btn.closest('.review-item');
            item.querySelector('.review-edit-form').classList.toggle('hidden');
        });
    });

    // Cancel edit
    document.querySelectorAll('.review-cancel-edit').forEach(btn => {
        btn.addEventListener('click', () => {
            btn.closest('.review-edit-form').classList.add('hidden');
        });
    });

});
