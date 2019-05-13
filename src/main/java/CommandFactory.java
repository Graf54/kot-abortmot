public class CommandFactory {


    /*private static Map<Integer, Class<? extends Command>> mapCommand;
    private static Map<Integer, String> mapDescription = new TreeMap<>();

    private static void init() {
        mapCommand = new HashMap<>();
        addCommand(id001_ShowAndEditMessage.class, "Показать сообщение");
        addCommand(id002_ChooseLanguage.class, "Выбрать язык");
        addCommand(id003_SwitchLanguage.class, "Сменить язык");
        addCommand(id004_ClearData.class, "Для одного человека которому нужно постоянно чистить данные");
        addCommand(id005_FindUser.class, "Поиск пользователя и редактор статуса");
        addCommand(id006_EditStatusUser.class, "редактор статуса");
        addCommand(id007_ShowAndEditInfo.class, "Показать информацию(проект, и т.п.)");
        addCommand(id008_ShowAndEditQuest.class, "Показать опрос");
        addCommand(id009_ShowInfoForAdmin.class, "Показать сообщение администратору");
        addCommand(id010_TestPsy.class, "Пройти психо тест");
        addCommand(id011_AddAndEditPsy.class, "Редактор теста");
        addCommand(id012_AddAndEditPsyType.class, "Редактор психотипа");
        addCommand(id014_Profile.class, "Заполнить анкету");
        addCommand(id015_AddAndEditProfile.class, "Изменить анкету");
        addCommand(id016_Registration.class, "Регистрация");
        addCommand(id019_Feedback.class, "Обратная связь");
        addCommand(id021_AnswerAdmin.class, "Ответ админа на обратную связь");
        addCommand(id022_AnswerUser.class, "Ответ пользователя на ответ админа");
        addCommand(id023_ReportByUser.class, "Отчет по пользователям");
        addCommand(id024_ReportByTest.class, "Отчет по тестированию");
        addCommand(id025_ReportByProfile.class, "Отчет по анкете");
        addCommand(id028_ShowResultQuest.class, "Показать результаты опроса");
        printListCommand();
    }


    public static Command getCommand(int id) {
        return getFormMap(id);
    }

    private static Command getFormMap(int id) {
        if (mapCommand == null) {
            init();
        }
        try {
            Class<? extends Command> aClass = mapCommand.get(id);
            if (aClass == null) {
                log.warn("Id {} no has command");
                return null;
            }
            return mapCommand.get(id).newInstance();
        } catch (Exception e) {
            log.error("Command caput: ", e);
        }
        return null;
    }


    *//*
      Добавляем наши Command, важно чтобы имелся id в названии класса
      *//*
    private static void addCommand(Class<? extends Command> commandClass, String description) {
        int id = -1;
        try {
            id = getId(commandClass.getName());
        } catch (Exception e) {
            log.warn("Command {} no has id, id set {}", commandClass, id);
        }
        if (id > 0 && mapCommand.get(id) != null) {
            log.warn("ID={} has duplicate command {} - {}", id, commandClass, mapCommand.get(id));
        }
        mapCommand.put(id, commandClass);
        mapDescription.put(id, description);
    }

    private static void addCommand(Class<? extends Command> commandClass) {
        addCommand(commandClass, "Описание не заданно");
    }

    private static int getId(String commandName) {
        return Integer.parseInt(commandName.split("_")[0].replaceAll("[^0-9]", ""));
    }

    private static void printListCommand() {
        StringBuilder stringBuilder = new StringBuilder();
        new TreeMap<>(mapCommand).forEach((y, x) -> stringBuilder.append(x.getSimpleName()).append("\n"));
        log.debug("List command:\n{}", stringBuilder.toString());
    }

    public static Map<Integer, String> getDescription() {
        return mapDescription;
    }*/
}
