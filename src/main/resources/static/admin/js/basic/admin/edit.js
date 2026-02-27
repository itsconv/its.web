$(function () {
  const $root = $('#adminEditForm');
  const $updateButton = $('#adminUpdateButton');

  $updateButton.on('click', function () {
    const isCreate = $root.data('isCreate') === true || $root.data('isCreate') === 'true';
    const userId = $('#userId').val();
    const password = $('#password').val();
    const name = $('#name').val();
    const memo = $('#memo').val();

    const url = isCreate ? '/api/admin/users' : `/api/admin/users/${userId}`;
    const method = isCreate ? 'POST' : 'PUT';

    $.ajax({
      url,
      method,
      contentType: 'application/json',
      data: JSON.stringify({
        userId,
        password,
        name,
        memo
      })
    })
      .then(function () {
        window.location.href = '/admin/user/list';
      })
      .catch(function () {
        if (isCreate) {
          alert('관리자 등록에 실패했습니다.');
          return;
        }
        alert('관리자 수정에 실패했습니다.');
      });
  });
});
