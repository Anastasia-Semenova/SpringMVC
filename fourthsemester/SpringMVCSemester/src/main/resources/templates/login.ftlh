<#ftl encoding="UTF-8"/>
<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring />
<head>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>

    <meta charset="utf-8">
    <title>Login</title>

</head>

<body class="bg-light">
<header>
    <nav>
        <a>Greennit</a>

        <div class="form-inline">
            <a href="?lang=ru" class="btn btn-light btn-lg" role="button" aria-pressed="true">RU</a>
            <a href="?lang=en" class="btn btn-light btn-lg" role="button" aria-pressed="true">EN</a>
        </div>
    </nav>
</header>
<div class="container">
    <div>
        <h3><@spring.message 'sign_in_page.login.title'/></h3>
    </div>

    <form class="registration text-center" method="post" action="/login">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <label for="login"><@spring.message 'sign_in_page.login.form.login'/></label>
        <div>
            <input type="text" name="login" id="login"
                   placeholder=<@spring.message 'sign_in_page.login.form.login.placeholder'/>>
        </div>
        <label for="hashPassword"><@spring.message 'sign_in_page.login.form.password'/></label>
        <input type="password" class="form-control" name="hashPassword" id="hashPassword"
               placeholder=<@spring.message 'sign_in_page.login.form.password.placeholder'/>>


        <div class="form-check">
            <label for="remember-me">
                <input type="checkbox"
                       name="remember-me"><@spring.message 'sign_in_page.login.form.remember-me'/>
            </label>
        </div>

        <input type="submit" class="btn btn-success" value=<@spring.message 'sign_in_page.login.form.submit'/>>
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="../assets/js/vendor/jquery.slim.min.js"><\/script>')</script>
</body>
</html>
