$(function () {
  const $actionButton = $('#adminRegisterBtn');
  $actionButton.on('click', function () {
    window.location.href = '/admin/user/edit';
  });

  const $deleteButtons = $('button[data-admin-delete="true"]');

  $deleteButtons.on('click', function () {
    const userId = $(this).data('userId');

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
});
