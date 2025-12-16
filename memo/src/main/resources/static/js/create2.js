const url = `http://localhost:8080/memo`;

const form = document.querySelector("#insert-form");

form.addEventListener("submit", (e) => {
  e.preventDefault();

  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ text: e.target.text.value }),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`error! ${res.status}`);
      }

      // json body 추출
      return res.json();
    })
    .then((data) => {
      console.log(data);
      if (data) {
        Swal.fire({
          title: "데이터 추가 완료",
          icon: "success",
          draggable: true,
        });
      }
      // location.reload(); // 새로고침
    })
    .catch((err) => console.log(err));
});
