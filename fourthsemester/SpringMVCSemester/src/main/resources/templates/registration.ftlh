<#ftl encoding="UTF-8"/>
<!doctype html>
<html lang="ru">
<#import "spring.ftl" as spring />
<head>
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>

    <meta charset="utf-8">
    <title>Registration</title>

</head>

<body>
<header>
    <nav>
        <a>Greennit</a>

        <div class="form-inline">
            <a href="?lang=ru" role="button" aria-pressed="true">RU</a>
            <a href="?lang=en" role="button" aria-pressed="true">EN</a>
        </div>
    </nav>
</header>
<div class="container">
    <div class="py-5 text-center">
        <!--
        <img class="d-block mx-auto mb-4" src="/resources/img/page_icon.png" width="85" height="85" alt="logo">
        -->
        <h3><@spring.message 'sign_up_page.registration.title'/></h3>
        <p class="lead"><@spring.message 'sign_up_page.registration.subtitle'/></p>
    </div>

    <@spring.bind "registrationFormDto"/>
    <#if namesErrorMessage ??>
        <p>  ${namesErrorMessage} </p>
    </#if>
    <form method="post" action="/registration">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <div>
                    <label for="firstName"><@spring.message 'sign_up_page.registration.form.name'/></label>
                    <div>
                        <#assign namePlaceholder><@spring.message 'sign_up_page.registration.form.name.placeholder'/></#assign>
                        <@spring.formInput "registrationFormDto.firstName" "class='form-control' placeholder='" + namePlaceholder + "' id='firstName'"/>
                        <@spring.showErrors "<br>" "text-danger"/>
                    </div>
                </div>

                <div>
                    <label for="lastName"><@spring.message 'sign_up_page.registration.form.lastname'/></label>
                    <div>
                        <#assign lastNamePlaceholder><@spring.message 'sign_up_page.registration.form.lastname.placeholder'/></#assign>
                        <@spring.formInput "registrationFormDto.lastName" "class='form-control' placeholder='" + lastNamePlaceholder + "' id='lastName'"/>
                        <@spring.showErrors "<br>" "text-danger"/>
                    </div>
                </div>

                <div>
                    <label for="email"><@spring.message 'sign_up_page.registration.form.email'/></label>
                    <div>
                        <#assign emailPlaceholder><@spring.message 'sign_up_page.registration.form.email.placeholder'/></#assign>
                        <@spring.formInput "registrationFormDto.email" "class='form-control' placeholder='" + emailPlaceholder + "' id='email'"/>
                        <@spring.showErrors "<br>" "text-danger"/>
                    </div>
                </div>

                <div>
                    <label for="hashPassword"><@spring.message 'sign_up_page.registration.form.password'/></label>
                    <div>
                        <#assign passwordPlaceholder><@spring.message 'sign_up_page.registration.form.password.placeholder'/></#assign>
                        <@spring.formPasswordInput "registrationFormDto.hashPassword" "class='form-control' placeholder='" + passwordPlaceholder + "' id='hashPassword' type='password'"/>
                        <@spring.showErrors "<br>" "text-danger"/>
                    </div>
                </div>

            <input type="submit" class="btn btn-success" value=<@spring.message 'sign_up_page.registration.form.submit'/>>

        <a href="/login"><@spring.message 'sign_up_page.registration.form.have_account'/></a>
    </form>

</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="../assets/js/vendor/jquery.slim.min.js"><\/script>')</script>
</body>
</html>
