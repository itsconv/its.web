(function () {
  const modal = document.getElementById("imageUploadModal");
  if (!modal) {
    return;
  }

  const dropzone = document.getElementById("imageUploadDropzone");
  const input = document.getElementById("imageUploadInput");
  const preview = document.getElementById("imageUploadPreview");
  const guideSize = document.getElementById("imageUploadGuideSize");
  const title = document.getElementById("imageUploadModalTitle");
  const submitButton = document.getElementById("imageUploadSubmitButton");
  const openButtons = document.querySelectorAll(".js-open-image-upload");
  const closeButtons = document.querySelectorAll(".js-close-image-upload");

  let selectedSlotId = null;
  let selectedFile = null;

  for (const button of openButtons) {
    button.addEventListener("click", function (event) {
      event.preventDefault();
      selectedSlotId = this.dataset.slotId;
      selectedFile = null;
      input.value = "";
      preview.src = "";
      preview.classList.remove("is-visible");
      guideSize.textContent = this.dataset.guideSize || "-";
      title.textContent = `${this.dataset.slotName} 수정`;
      modal.classList.add("is-open");
    });
  }

  for (const button of closeButtons) {
    button.addEventListener("click", closeModal);
  }

  modal.addEventListener("click", function (event) {
    if (event.target === modal) {
      closeModal();
    }
  });

  dropzone.addEventListener("dragover", function (event) {
    event.preventDefault();
    dropzone.classList.add("is-dragover");
  });

  dropzone.addEventListener("dragleave", function () {
    dropzone.classList.remove("is-dragover");
  });

  dropzone.addEventListener("drop", function (event) {
    event.preventDefault();
    dropzone.classList.remove("is-dragover");

    const file = event.dataTransfer.files[0];
    applyFile(file);
  });

  input.addEventListener("change", function () {
    applyFile(this.files[0]);
  });

  submitButton.addEventListener("click", async function () {
    if (!selectedSlotId || !selectedFile) {
      alert("업로드할 이미지를 선택해 주세요.");
      return;
    }

    const formData = new FormData();
    formData.append(
      "request",
      new Blob(
        [JSON.stringify({ altText: "", linkUrl: "" })],
        { type: "application/json" }
      )
    );
    formData.append("file", selectedFile);

    submitButton.disabled = true;

    $.ajax({
      url: `/api/admin/images/${selectedSlotId}/file`,
      method: 'POST',
      data: formData,
      processData: false,
      contentType: false,
    })
      .then(function () {
        window.location.reload();
      })
      .catch(function (err) {
        alert(err.responseJSON?.message || "이미지 수정에 실패했습니다.");
      })
      .always(function () {
        submitButton.disabled = false;
      });
  });

  function applyFile(file) {
    if (!file) {
      return;
    }

    if (!file.type.startsWith("image/")) {
      alert("이미지 파일만 업로드할 수 있습니다.");
      return;
    }

    selectedFile = file;

    const reader = new FileReader();
    reader.onload = function (event) {
      preview.src = event.target.result;
      preview.classList.add("is-visible");
    };
    reader.readAsDataURL(file);
  }

  function closeModal() {
    modal.classList.remove("is-open");
    dropzone.classList.remove("is-dragover");
  }
})();
