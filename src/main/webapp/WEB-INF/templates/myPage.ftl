<#-- @ftlvariable name="img" type="java.lang.String" -->
<#-- @ftlvariable name="username" type="String" -->
<#-- @ftlvariable name="userid" type="String" -->
<#import "components/components.ftl" as comp>
<@comp.base>
    <@comp.header username=username userid=userid/>

  <main class="content__wrapper">
    <div class="content">
      <section class="info">
          <@comp.label text=pageName>
            <div class="info__wrapper">
              <div style="background-image: url('/${img}')" class="info__img"></div>

              <div class="info__content">
                  <#list fields as name, value>
                      <#if (value?is_string && value != "")>
                          <@comp.label text=name>
                              ${value}
                          </@comp.label>
                      </#if>
                  </#list>
                <a href="edit-me">
                  <button>
                    Редактировать
                  </button>
                </a>
              </div>
            </div>
          </@comp.label>
      </section>

      <section class="lists">
          <#list lists as name, value>
              <@comp.label text=name>
                <div class="hor-list">
                    <#list value.list as el>
                      <a class="list__item" href="${value.url}?id=${el.id}">
                        <div class="list__item-img" style="background-image: url('/${el.img}')"></div>

                          <#list el.fields as value2>
                            <div class="list__item-label">
                                ${value2}
                            </div>
                          </#list>
                      </a>
                    </#list>
                </div>
              </@comp.label>
          </#list>
      </section>

        <#if type??>
            <@comp.comments userId=userid comments=comments entityType="${type}"/>
        </#if>
    </div>
  </main>

    <#include "components/footer.ftl"/>
</@comp.base>
