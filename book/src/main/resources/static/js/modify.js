document.querySelector(".btn-danger").addEventListener("click", (e) => {
  document.querySelector("form").action = "/book/remove";
  document.querySelector("form").submit();
});
