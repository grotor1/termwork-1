<#macro label text>
  <div class="labeled-field">
    <span class="labeled-field__label">${text}</span>
      <#nested>
  </div>
</#macro>

<#macro error text link>
  <a class="errors" href="${link}">
    <div class="error">
        ${text}
    </div>
  </a>
</#macro>

<#macro base>
  <!DOCTYPE html>
  <html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="styles/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <script type="text/javascript" src="./scripts/jquery-3.7.1.min.js"></script>
  </head>
  <body>
  <#nested>
  </body>
  <script type="text/javascript" src="./scripts/index.js"></script>
  <script type="text/javascript" src="./scripts/loadList.js"></script>
  </html>
</#macro>

<#macro header username="" userid="">
  <header class="header__wrapper">
    <div class="header">
      <a href="./" class="logo">
        neet shelter
      </a>

      <div class="nav">
        <a href="./" class="nav__item">
          Главная
        </a>
        <a href="posts" class="nav__item">
          Посты
        </a>
        <a href="animes" class="nav__item ">
          Аниме
        </a>
        <a href="сharacters" class="nav__item ">
          Персонажи
        </a>
        <a href="persons" class="nav__item ">
          Люди
        </a>

          <#if username?has_content>
            <a class="account" href="user?id=${userid}">
              <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="#f1efef" viewBox="0 0 256 256">
                <path
                        d="M128,24A104,104,0,1,0,232,128,104.11,104.11,0,0,0,128,24ZM74.08,197.5a64,64,0,0,1,107.84,0,87.83,87.83,0,0,1-107.84,0ZM96,120a32,32,0,1,1,32,32A32,32,0,0,1,96,120Zm97.76,66.41a79.66,79.66,0,0,0-36.06-28.75,48,48,0,1,0-59.4,0,79.66,79.66,0,0,0-36.06,28.75,88,88,0,1,1,131.52,0Z"></path>
              </svg>

              <span>${username}</span>
            </a>
            <a href="logout" class="nav__item">
              Выйти
            </a>
          <#else>
            <a href="login" class="nav__item">
              Вход
            </a>
            <a href="registration" class="nav__item">
              Регистрация
            </a>
          </#if>
      </div>
    </div>
  </header>
</#macro>

<#macro pagination curPage pageCount url query="">
  <div class="pagination">
      <#list 1..pageCount as page>
          <#assign pageUrl = "${url}?page=${page}&q=${query}">
          <#assign cur = page == curPage>
        <a href="${pageUrl}" class="pagination__item ${cur?then("pagination__item_cur", "")}">
            ${page}
        </a>
      </#list>
  </div>
</#macro>

<#macro rating entityId rating userid="">
  <div class="reaction-input">
    <button ${userid???then("", "disabled")} class="reaction-input__button react-like" entity-id="${entityId}">
      +
    </button>
    <span>${rating}</span>
    <button ${userid???then("", "disabled")} class="reaction-input__button react-dislike" entity-id="${entityId}">
      -
    </button>
  </div>
</#macro>

<#macro rating entityId rating userid="">
  <div class="reaction-input">
    <button ${userid???then("", "disabled")} class="reaction-input__button react-like" entity-id="${entityId}">
      +
    </button>
    <span>${rating}</span>
    <button ${userid???then("", "disabled")} class="reaction-input__button react-dislike" entity-id="${entityId}">
      -
    </button>
  </div>
</#macro>

<#macro comment img name date text userId>
  <div class="comment">
    <img src="/${img}" class="comments__img">

    <div class="comments__content">
      <div class="comments__info">
        <a href="user?id=${userId}">${name}</a>

        <span>${date}</span>
      </div>

      <span>
        ${text}
      </span>
    </div>

  </div>
</#macro>

<#macro comments comments entityType userId="">
  <section class="comments">
      <@label text='Комментарии'>
          <#if userId?has_content>
            <form class="comment__input" action="comment" method="post" id="send">
              <label for="text">
                Введите комментарий
              </label>
              <textarea rows="5" required class="comment__input-field" placeholder="Комментарий"
                        name="text"
                        id="commentText"></textarea>
              <input type="text" style="display: none" name="type" value="${entityType}">
              <input id="entityId" type="text" style="display: none" name="entityId">
              <div class="comment__input-bottom">
                <span><span id="count">0</span>/400</span>

                <input class="comment__input-submit" type="submit" value="Оставить комментарий">
              </div>
            </form>
          </#if>

          <#list comments as com>
              <@comment img=com.img name=com.name userId=com.userId date=com.date text=com.text/>
          </#list>
      </@label>
  </section>
</#macro>
