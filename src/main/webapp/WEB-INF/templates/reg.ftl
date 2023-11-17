<#-- @ftlvariable name="error" type="String" -->
<#import "components/components.ftl" as comp>
<@comp.base>
  <main class="form__wrapper">
    <form method="post" class="form">
        <@comp.label text="Вход">
            <@comp.label text="Логин">
              <input type="text" name="login">

            </@comp.label>

            <@comp.label text="Имя">
              <input type="text" name="name">

            </@comp.label>

            <@comp.label text="Пароль">
              <input type="password" name="password">

            </@comp.label>

            <@comp.label text="Повтор пароля">
              <input type="password" name="passwordRep">

            </@comp.label>

          <a href="login">Уже зарегистрирован</a>

          <input type="submit" value="Зарегистрироваться">
        </@comp.label>
    </form>
  </main>
    <#if error??>
        <@comp.error text=error link="registration"/>
    </#if>
</@comp.base>