<#-- @ftlvariable name="username" type="String" -->
<#-- @ftlvariable name="userid" type="String" -->
<#import "components/components.ftl" as comp>
<@comp.base>
    <@comp.header username=username userid=userid/>
  <main class="content__wrapper">
    <div class="content">
      <section class="recent-posts">
        <div class="labeled-field">
          <div class="labeled-field__label">Посты</div>

            <#if userid??>
              <a href="post-create">
                <button>
                  Создать пост
                </button>
              </a>
            </#if>

          <div class="post-list">
              <#list posts as el>
                <a href="post?id=${el["id"]}" class="post-list__item">
                  <div class="post-list__item-img" style="background-image: url('/${el["img"]}')"></div>

                  <span class="post-list__item-date">${el["date"]}</span>

                  <span class="post-list__item-title">${el["title"]}</span>
                </a>
              </#list>
          </div>

            <@comp.pagination pageCount=pageCount curPage=curPage url="posts"/>
        </div>
      </section>
    </div>
  </main>

    <#include "components/footer.ftl"/>
</@comp.base>
