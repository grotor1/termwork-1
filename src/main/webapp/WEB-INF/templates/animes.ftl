<#-- @ftlvariable name="username" type="String" -->
<#-- @ftlvariable name="userid" type="String" -->
<#import "components/components.ftl" as comp>
<@comp.base>
    <@comp.header username=username userid=userid/>
  <main class="content__wrapper">
    <div class="content">
      <section class="recent-posts">
        <div class="labeled-field">
          <div class="labeled-field__label">Аниме</div>

          <div class="labeled-field">
            <div class="labeled-field__label">Поиск</div>
            <form class="find-form" action="">
              <input class="find-form__input" placeholder="Запрос" name="q" value="${query???then(query, '')}"
                     type="text">

              <input type="submit" value="Найти">
            </form>
          </div>

          <div class="anime-list">
              <#list animes as el>
                <a class="list__item" href="anime?id=${el["id"]}">
                  <div class="list__item-img" style="background-image: url('/${el["img"]}')"></div>

                  <div class="list__item-label">
                      ${el["title"]}
                  </div>
                  <div class="list__item-label">
                      ${el["type"]}
                  </div>
                  <div class="list__item-label">
                      ${el["status"]}
                  </div>
                  <div class="list__item-label">
                      ${el["date"]}
                  </div>
                </a>
              </#list>
          </div>

            <@comp.pagination pageCount=pageCount curPage=curPage url="animes" query=query/>
        </div>
      </section>
    </div>
  </main>

    <#include "components/footer.ftl"/>
</@comp.base>
