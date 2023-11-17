const inputHandler = (element, max) => {
    element.addEventListener("input", (e) => {
        if (e.target.value.length > max) {
            e.target.value = e.target.value.slice(0, max);
        }

        document.getElementById("count").innerText = e.target.value.length;
    })

    document.getElementById("count").innerText = element.value.length;
}

const commentInput = document.getElementById("commentText");

if (commentInput) {
    inputHandler(commentInput, 400)
}

const textInput = document.getElementById("postText");

if (textInput) {
    inputHandler(textInput, 5000)
}

const enitityIdInput = document.getElementById("entityId");

if (enitityIdInput) {
    const params = new URLSearchParams(location.search);
    enitityIdInput.value = params.get("id");
}