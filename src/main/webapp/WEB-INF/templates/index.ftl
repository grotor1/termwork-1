<#-- @ftlvariable name="username" type="String" -->
<#-- @ftlvariable name="userid" type="String" -->
<#import "components/components.ftl" as comp>
<@comp.base>
    <@comp.header username=username userid=userid/>

  <main class="content__wrapper">

    <div class="content">
      <section class="recent-anime">
        <div class="labeled-field">
          <div class="labeled-field__label">Недавно вышло</div>

          <div class="hor-list">
              <#list recentAnime as el>
                <a class="list__item" href="anime?id=${el["id"]}">
                  <div class="list__item-img" style="background-image: url('/${el["img"]}')"></div>

                  <div class="list__item-label">
                      ${el["title"]}
                  </div>
                  <div class="list__item-label">
                      ${el["date"]}
                  </div>
                </a>
              </#list>
          </div>
        </div>
      </section>

      <section class="recent-posts">
        <div class="labeled-field">
          <div class="labeled-field__label">Недавние посты</div>

          <div class="post-list">
              <#list recentPost as el>
                <a href="post?id=${el["id"]}" class="post-list__item">
                  <div class="post-list__item-img" style="background-image: url('/${el["img"]}')"></div>

                  <span class="post-list__item-date">${el["date"]}</span>

                  <span class="post-list__item-title">${el["title"]}</span>
                </a>

              </#list>
          </div>
        </div>
      </section>
    </div>
  </main>

    <#include "components/footer.ftl"/>
</@comp.base>
