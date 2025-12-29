// 등록 클릭 시 (form submit 시)
document.querySelector("#createForm").addEventListener("submit", (e) => {
  // submit 기능 중지. 왜?
  e.preventDefault();
  // uploadResult안 li 정보 수집 후 form hidden 태그로 append 해야하니까.

  const attachInfos = document.querySelectorAll(".uploadResult li");

  let result = "";

  attachInfos.forEach((obj, idx) => {
    result += `<input type="hidden" name="movieImages[${idx}].imgName" value="${obj.dataset.name}">`;
    result += `<input type="hidden" name="movieImages[${idx}].uuid" value="${obj.dataset.uuid}">`;
    result += `<input type="hidden" name="movieImages[${idx}].path" value="${obj.dataset.path}">`;
  });

  e.target.insertAdjacentHTML("beforeend", result);

  console.log(e.target.innerHTML);

  e.target.submit();
});
