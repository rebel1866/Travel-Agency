# Travel-Agency
Тема проекта - турагентство.
В веб-приложении реализован функционал по чтению, добавлению, удалению, измененению туров, отелей, пользователей, заявок на туры, личной информации 
пользователя и т.д. Для определения предпочтений пользователя используеются куки. Веб-приложение использует многопоточную логику для обновления состояния
базы данных и отслеживания актуальных курсов валют. Для просмотра длинных списков применяется пагинация. Из базы данных, с которой работает
веб-приложение ничего не удаляется (меняется статус поля на DELETED). Приложение написано с учетом требований интернационализации и
локализовано на английский и русский языки. Для фронтенда не использовались фреймворки, графический интерфейс написан самостоятельно,
стили css лежат в папке WEB-INF/css. Реализована защита от  F5, также блокируется попытка пользователя вернуться к той же форме, после отправки 
данных. В ряде случаев применяются custom tags. Для удобства поиска используется ajax. Реализован механизм по восстановлению пароля  - в случае,
если пользователь забыл его, он получает письмо на почту с новым паролем. Для регистрации также нужно перейти по ссылке, указанной в письме, высланном
на почту. Имеется защита от js и sql injections. Валидация данных производится на стороне как клиента, так и сервера.
Схема бд прилагается.

Роли пользователя:
1) Гость - имеет возможность просматривать информацию о турах, отелях, курортах, но не может оставлять заявки на тур и комментарии к турам.
2) Обычный пользователь (common)  - может делать все то, что и Гость + оставлять комментарии и заявки на тур
3) Админы - главный админ может добавлять, удалять, изменять туры, отели, заявки, пользователей. Также главный админ может добавлять другие
роли пользователей, например, админов, с более ограниченным набором прав.
