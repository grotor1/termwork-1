const listEl = document.getElementById("list");

let index = 0;

const addItem = (id, type) => {
    const wrapper = document.createElement("div");
    wrapper.classList.add("element__input");
    const titleList = document.createElement("select");
    for (let i = 0; i < document.getElementById("anime").childElementCount; i++) {
        const node = document.getElementById("anime").children.item(i).cloneNode(true);
        if (id === node.getAttribute("value")) {
            node.setAttribute("selected", "");
        }
        titleList.appendChild(node);
    }
    titleList.classList.add("element__input-select");
    titleList.setAttribute("name", index + "title");

    const typeList = document.createElement("select");
    for (let i = 0; i < document.getElementById("types").childElementCount; i++) {
        const node = document.getElementById("types").children.item(i).cloneNode(true);
        if (type === node.getAttribute("value")) {
            node.setAttribute("selected", "");
        }
        typeList.appendChild(node);
    }
    typeList.classList.add("element__input-select");
    typeList.setAttribute("name", index + "type");

    wrapper.appendChild(titleList);
    wrapper.appendChild(typeList);

    listEl.prepend(wrapper);
    index++;
}

if (document.getElementById("userForm")) {
    $.ajax("anime-list-ajax", {
        type: "get",
        success: (allListData) => {
            const datalist = document.getElementById("anime");
            const allList = JSON.parse(allListData);
            if (datalist) {
                allList.forEach((item) => {
                    const option = document.createElement("option");
                    option.setAttribute("value", item.id);
                    option.innerText = item.title;
                    datalist.append(option);
                });
            }

            $.ajax("user-anime-list-ajax", {
                type: "get",
                success: (userListData) => {
                    const userList = JSON.parse(userListData);
                    if (userList) {
                        userList.forEach((item) => {
                            addItem(item.id, item.type);
                        });
                    }
                }
            })
        }
    })
}

$.ajax("anime-list-ajax", {
    type: "get",
    success: (allListData) => {
        const datalist = document.getElementById("animeId");
        const allList = JSON.parse(allListData);
        if (datalist) {
            allList.forEach((item) => {
                const option = document.createElement("option");
                option.setAttribute("value", item.id);
                option.innerText = item.title;
                datalist.append(option);
            });
        }
    }
})

document.getElementById("add")?.addEventListener("click", (ev) => {
    ev.preventDefault();
    addItem("", "Смотрю")
})

document.getElementById("userForm")?.addEventListener("submit", (ev) => {
    ev.preventDefault();


    const formData = new FormData(document.getElementById("userForm"));
    for (const [key, value] of formData.entries()) {
        if (key.endsWith("title")) {
            const el = value + ":" + formData.get(key.slice(0, -5) + "type");
            const list = (formData.get("list") ? formData.get("list") + "," : "") + el;
            formData.delete("list");
            formData.set("list", list);
        }
    }
    if (formData.get("photo").size === 0) {
        formData.delete("photo");
    }

    $.ajax("edit-me", {
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        success: () => {
            location.assign("user?id=" + document.getElementById("userid").innerText);
        }
    })
})