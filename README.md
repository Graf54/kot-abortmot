# kot-abortmot
<a>Используется:
<br />Kotlin
<br />Spring JPA
<br />
<br />Бот автоотчетчик. Создаем набор кнопок с автоответами
<br />Присутствует регистрация, анкетирование пользователя
<br />Структура бота
<br />Получаем upda<br />te -> смотрим от кого -> смотрим что за комманда/кнопка -> берем нужную Command -> выполняем Command
<br />Получается вся логика в классах Command 
<br />Логика с работой command 1: 
  <br />-> создаем новую кнопку(к примеру "Привет") 
    <br />-> назначаем кнопке комманд 1(показ сообщения) 
      <br />-> назначаем сообщение
        <br />-> DONE
<br />Чтобы добавить новую логику нужно создать новый класс от Command - к примеру show water(5)
<br />добавить кнопку с номером этого (Whather)
        
