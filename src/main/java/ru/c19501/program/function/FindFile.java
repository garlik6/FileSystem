//package ru.c19501.program.function;
//
//import ru.c19501.system.FileSystem;
//import ru.c19501.model.FileRecord.FileRecordReturnDTO;
//import ru.c19501.program.struct.iCommand;
//import ru.c19501.program.struct.iMonitor;
//import ru.c19501.service.CoreService;
//import ru.c19501.service.CoreServiceImpl;
//import ru.c19501.service.config.JacksonConfig;
//
//public class FindFile extends BaseCommand implements iCommand {
//
//    protected String[] fileName = new String[3];
//
//    public FindFile(iMonitor im, FileSystem fileSystem) {
//        super(im, fileSystem);
//    }
//
//
//    @Override
//    public void execute(FileSystem fs) {
//        readParameters();
//        CoreService coreService = new CoreServiceImpl(fs, JacksonConfig.createObjectMapper());
//        FileRecordReturnDTO file = coreService.foundFile(fileName[1], fileName[2]);
//        if (file == null) {
//            System.out.println("Файл " + fileName[1] + "." + fileName[2] + " не найден в системе.");
//        } else
//            System.out.println("Файл " + fileName[1] + "." + fileName[2] + " найден. Дата и время создания:" + file.getCreationDate());
//        fs.save();
//    }
//
//    @Override
//    public void readParameters() {
//        this.fileName[1] = monitor.readString("Введите имя файла");
//        this.fileName[2] = monitor.readString("Введите тип файла");
//    }
//
//}
