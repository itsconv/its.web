$(function () {
  const $root = $('#popupEditForm');
  const $saveButton = $('#popupSaveButton');
  const editorElement = $('#popupEditor').get(0);
  const contentsSource = $('#contents').get(0);

  const editor = new toastui.Editor({
    el: editorElement,
    height: '380px',
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    toolbarItems: [
      ['heading', 'bold', 'italic', 'strike'],
      ['hr', 'quote'],
      ['ul', 'ol', 'task'],
      ['table', 'image', 'link'],
      ['code', 'codeblock']
    ],
    initialValue: contentsSource.value || ''
  });

  if (window.ResizeObserver) {
    const resizeObserver = new ResizeObserver(function () {
      const nextHeight = Math.max(editorElement.clientHeight, 380);
      editor.setHeight(`${nextHeight}px`);
    });
    resizeObserver.observe(editorElement);
  }

  $saveButton.on('click', function () {
    const isCreate = $root.data('isCreate') === true || $root.data('isCreate') === 'true';
    const seq = $root.data('seq');

    const title = $('#title').val();
    const contents = editor.getHTML();
    const startDate = $('#startDate').val();
    const endDate = $('#endDate').val();
    const useYn = $('input[name="useYn"]:checked').val();
    const positionX = $('#positionX').val();
    const positionY = $('#positionY').val();
    const sizeW = $('#sizeW').val();
    const sizeH = $('#sizeH').val();
    const redirectUrl = $('#redirectUrl').val();

    const url = isCreate ? '/api/admin/popups' : `/api/admin/popups/${seq}`;
    const method = isCreate ? 'POST' : 'PUT';

    $.ajax({
      url,
      method,
      contentType: 'application/json',
      data: JSON.stringify({
        title,
        contents,
        startDate,
        endDate,
        useYn,
        positionX: Number(positionX),
        positionY: Number(positionY),
        sizeW: Number(sizeW),
        sizeH: Number(sizeH),
        redirectUrl
      })
    })
      .then(function () {
        window.location.href = '/admin/popup/list';
      })
      .catch(function () {
        if (isCreate) {
          alert('팝업 등록에 실패했습니다.');
          return;
        }
        alert('팝업 수정에 실패했습니다.');
      });
  });
});
