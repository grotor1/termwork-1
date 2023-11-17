<#-- @ftlvariable name="error" type="String" -->
<#import "components/components.ftl" as comp>
<@comp.base>
  <main class="form__wrapper">
    <form method="post" class="form">
        <@comp.label text="Вход">
            <@comp.label text="Логин">
              <input type="text" minlength="4" max="16" name="login" required>

            </@comp.label>

            <@comp.label text="Пароль">
              <input type="password" minlength="4" max="16" name="password" required>

            </@comp.label>

          <label class="checkbox__input">
            <input type="checkbox" name="remember">

            Запомнить меня
          </label>

          <input type="submit" value="Войти">
        </@comp.label>
    </form>
  </main>
    <#if error??>
        <@comp.error text=error link="login"/>
    </#if>
</@comp.base>