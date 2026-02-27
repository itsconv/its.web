document.addEventListener('DOMContentLoaded', function () {
  const root = document.getElementById('adminEditForm');
  const updateButton = document.getElementById('adminUpdateButton');
  if (!root || !updateButton) return;

  updateButton.addEventListener('click', function () {
    const userId = root.dataset.userId;
    const password = document.getElementById('password').value;
    const name = document.getElementById('name').value;
    const memo = document.getElementById('memo').value;

    $.ajax({
      url: `/api/admin/users/${userId}`,
      method: 'PUT',
      contentType: 'application/json',
      data: JSON.stringify({
        password,
        name,
        memo
      })
    })
      .then(function () {
        window.location.href = '/basic/admin_list';
      })
      .catch(function () {
        alert('관리자 수정에 실패했습니다.');
      });
  });
});
