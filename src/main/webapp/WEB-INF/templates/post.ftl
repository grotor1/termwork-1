<#-- @ftlvariable name="username" type="String" -->
<#-- @ftlvariable name="userid" type="String" -->
<#import "components/components.ftl" as comp>
<@comp.base>
    <@comp.header username=username userid=userid/>

  <main class="content__wrapper">
    <div class="content">
      <section class="post">
        <span class="post__date">${date}</span>
        <span>Пост о: <a href="anime?id=${animeId}">${animeTitle}</a></span>
        <span>Автор: <a href="user?id=${userId}">${username}</a></span>
        <span class="post__title">${title}</span>
        <img class="post__img" src="/${img}">

        <span class="post__text">${text}</span>
      </section>

        <@comp.comments comments=comments entityType="post" userId=userid/>
    </div>
  </main>


    <#include "components/footer.ftl"/>
</@comp.base>
