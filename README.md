# bankApplication

Приложение, реализующее логику банковской системы.

##### Доступно три режима работы: #####
- Командный интерфейс (BankCommander)
- Клиент/сервер (BankServer/BankClient)
- Многопоточный сервер (BankServerThreaded/BankClient)

##### Командный интерфейс #####
`Сохранение в файл .feed`<br/>
Доступно сохранение информации о всех клиентах банка в файл .feed<br/>
Одна строка соответствует одному счету клиента<br/>
Формат файла .feed:<br/>
"accountType=c;id=1;balance=-190.25886108623172;overdraft=500.0;name=John;gender=m;city=New York"<br/>

`Загрузка из файлов .feed`<br/>

`Операции на уровне банка`<br/>
Краткая информация о банке<br/>
Краткая информация о клиентах<br/>
Добавление клиента<br/>

`Операции на уровне клиента`<br/>
Информация о счетах<br/>
Снятие, депозит<br/>
Перевод другому клиенту<br/>

`Сериализация клиентов`<br/>

##### Клиент/сервер #####
`Баланс`<br/>
`Снятие`<br/>

##### Многопоточный сервер #####
Одновременное подключение n клиентов к серверу<br/>

`Мониторинг`<br/>
Вывод количества подсоединенных клиентов 1 раз в 10 с<br/>

##### Тесты #####
Тест сериализации клиентов<br/>
Тест конкурентного обращения 10000 потоков к одному счету<br/>



