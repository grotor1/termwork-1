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
          <form id="userForm" action="./" method="post" class="form_edit" enctype="multipart/form-data">
              <@comp.label text="Имя">
                <input required type="text" value="${name}" name="name">
              </@comp.label>

              <@comp.label text="Биография">
                <textarea required name="bio" id="commentText">${bio}</textarea>

                <span><span id="count">0</span>/400</span>
              </@comp.label>

              <@comp.label text="Аватар">
                <input type="file" accept="image/*" name="photo">
              </@comp.label>

              <@comp.label text="Список аниме">
                <div class="form_edit" id="list">
                  <button id="add">+ Добавить</button>
                </div>
              </@comp.label>

            <datalist id="types">
              <option value="Смотрю">Смотрю</option>
              <option value="Просмотренно">Просмотренно</option>
              <option value="Брошенно">Брошенно</option>
              <option value="Любимое">Любимое</option>
              <option value="В планах">В планах</option>
            </datalist>

            <datalist id="anime"></datalist>

            <input type="submit" value="Изменить">
          </form>
        </@comp.label>
    </div>
  </main>

    <#include "components/footer.ftl"/>
</@comp.base>
