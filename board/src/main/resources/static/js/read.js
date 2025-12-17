const url = `http://localhost:8080/replies`;
const replyList = document.querySelector(".replyList");

// 댓글 목록 가져오기
const loadReply = () => {
  fetch(`${url}/board/${bno}`)
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }

      return res.json();
    })
    .then((data) => {
      console.log(data);

      // 댓글 개수 보여주기
      document.querySelector(".fw-semibold span").textContent = data.length;
      let result = "";

      data.forEach((reply) => {
        result += `
        <div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}">
          <div class="p-3">
            <img
              src="/img/user.png"
              class="rounded-circle mx-auto d-block"
              style="width: 60px; height: 60px"
            />
          </div>
  
          <div class="flex-grow-1 align-self-center">
            <div>${reply.replyer}</div>
            <div>
              <span class="fs-5">${reply.text}</span>
            </div>
            <div class="text-muted">
              <span class="small">${formatDate(reply.createDate)}</span>
            </div>
          </div>
  
          <div class="d-flex flex-column align-self-center">
            <div class="mb-2">
              <button class="btn btn-outline-danger btn-sm">
                삭제
              </button>
            </div>
            <div class="mb-2">
              <button class="btn btn-outline-success btn-sm">
                수정
              </button>
            </div>
          </div>
        </div>
      `;
      });

      replyList.innerHTML = result;
    })
    .catch((err) => console.log(err));
};

// 댓글 추가
// 댓글 작성 클릭 시 == replyForm submit 발생 시
document.querySelector("#replyform").addEventListener("submit", (e) => {
  // submit 기능 중지
  e.preventDefault();

  const form = e.target;
  const rno = form.rno.value;
  const reply = { rno: rno, text: form.text.value, replyer: form.replyer.value, bno: bno };

  if (!rno) {
    // new
    fetch(`${url}/new`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(reply),
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
            title: "댓글 작성 완료",
            icon: "success",
            draggable: true,
          });
        }
        document.querySelector("#replyform #replyer").value = "";
        document.querySelector("#replyform #text").value = "";
        // 댓글 가져오기
        loadReply();
      })
      .catch((err) => console.log(err));
  } else {
    // modify
    fetch(`${url}/${rno}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(reply),
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
            title: "댓글 수정 완료",
            icon: "success",
            draggable: true,
          });
        }
        form.replyer.value = "";
        form.text.value = "";
        form.rno.value = "";

        form.rbtn.innerHTML = "댓글 작성";
        // 댓글 가져오기
        loadReply();
      })
      .catch((err) => console.log(err));
  }
});

// 날짜 / 시간
const formatDate = (data) => {
  const date = new Date(data);
  // 2025/12/16 12:20
  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

loadReply();

// 이벤트 버블링으로 댓글 삭제
replyList.addEventListener("click", (e) => {
  console.log(e.target); // 어느 버튼의 이벤트인가?
  const btn = e.target;

  // 부모 쪽으로만 검색
  // data- 접근 : dataset
  const rno = btn.closest(".reply-row").dataset.rno;
  console.log("rno", rno);

  // 삭제와 수정
  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;

    fetch(`${url}/${rno}`, {
      method: "DELETE",
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        return res.text();
      })
      .then((data) => {
        if (data) {
          // 댓글 다시 가져오기
          loadReply();
        }
      })
      .catch((err) => console.log(err));
  } else if (btn.classList.contains("btn-outline-success")) {
    // rno를 이용해 reply 가져오기
    // 가져온 reply를 replyForm에 보여주기
    const form = document.querySelector("#replyform");
    // 댓글 작성 버튼 => 댓글 수정
    fetch(`${url}/${rno}`)
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }

        // json body 추출
        return res.json();
      })
      .then((data) => {
        console.log(data);

        form.rno.value = data.rno;
        form.replyer.value = data.replyer;
        form.text.value = data.text;

        // 버튼 텍스트 변경
        form.rbtn.innerHTML = "댓글 수정";
      })
      .catch((err) => console.log(err));
  }
});

// document.querySelectorAll(".btn-outline-danger").forEach((btn) => {
//   btn.addEventListener("click", (e) => {
//     const targetBtn = e.target;

//     const rno = targetBtn.closest(".reply-row").dataset.rno;
//     console.log(rno);
//   });
// });
