// 검색 폼 submit 시
document.querySelector("#actionForm").addEventListener("submit", (e) => {
  // submit 중지
  e.preventDefault();

  const form = e.target;
  const type = form.querySelector("select[name='type']").value;
  const keyword = form.querySelector("input[name='keyword']").value;
  // keywword, select 값이 있는지 확인
  // 없다면 메세지
  if (!type) {
    alert("검색 타입을 선택하세요.");
    return;
  }
  if (!keyword) {
    alert("검색어를 입력하세요.");
    return;
  }
  // page 값을 1로 변경
  form.page.value = 1;
  form.submit();
});
