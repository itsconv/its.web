$(function () {
  const $actionButton = $('#popupRegisterBtn');
  $actionButton.on('click', function () {
    window.location.href = '/admin/popup/edit';
  });

  const $deleteButtons = $('button[data-popup-delete="true"]');

  $deleteButtons.on('click', function () {
    const seq = $(this).data('seq');

    if (!confirm('정말 삭제하시겠습니까?')) {
      return;
    }

    $.ajax({
      url: `/api/admin/popups/${seq}`,
      method: 'DELETE'
    })
      .then(function () {
        window.location.reload();
      })
      .catch(function () {
        alert('팝업 삭제에 실패했습니다.');
      });
  });
});
