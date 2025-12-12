document.querySelector(".btn-danger").addEventListener("click", (e) => {
  document.querySelector("form").action = "/board/remove";
  document.querySelector("form").submit();
});
