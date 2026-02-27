document.addEventListener('DOMContentLoaded', function () {
  const deleteButtons = document.querySelectorAll('button[data-admin-delete="true"]');
  if (deleteButtons.length === 0) return;

  for (const button of deleteButtons) {
    button.addEventListener('click', function () {
      const userId = button.dataset.userId;
      if (!userId) return;

      if (!confirm('정말 삭제하시겠습니까?')) {
        return;
      }

      $.ajax({
        url: `/api/admin/users/${userId}`,
        method: 'DELETE'
      })
        .then(function () {
          window.location.reload();
        })
        .catch(function () {
          alert('관리자 삭제에 실패했습니다.');
        });
    });
  }
});
