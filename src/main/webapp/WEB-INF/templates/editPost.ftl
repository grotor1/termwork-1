<#-- @ftlvariable name="img" type="java.lang.String" -->
<#-- @ftlvariable name="username" type="String" -->
<#-- @ftlvariable name="userid" type="String" -->
<#import "components/components.ftl" as comp>
<span id="userid" style="display: none">${userid}</span>
<@comp.base>
    <@comp.header username=username userid=userid/>

  <main class="content__wrapper">
    <div class="content">
        <@comp.label text="Редактирование">
          <form method="post" class="form_edit" enctype="multipart/form-data">
              <@comp.label text="Заголовок">
                <input required type="text" name="title">
              </@comp.label>

              <@comp.label text="Текст">
                <textarea required name="text" id="postText"></textarea>

                <span><span id="count">0</span>/5000</span>
              </@comp.label>

              <@comp.label text="Привязанное аниме">
                <select required name="animeId" id="animeId"/>
              </@comp.label>

              <@comp.label text="Фото">
                <input required type="file" accept="image/*" name="photo">
              </@comp.label>

            <input type="submit" value="Создать">
          </form>
        </@comp.label>
    </div>
  </main>

    <#include "components/footer.ftl"/>
</@comp.base>
