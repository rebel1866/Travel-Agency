package by.epamtc.stanislavmelnikov.controller.executor;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.factory.CommandProvider;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.OrderLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor extends HttpServlet {
    private static CommandProvider provider = new CommandProvider();
    private static final String commandKey = "command";
    private static final String cacheControlKey = "Cache-Control";
    private static final String cacheControlVal = "private, no-store, no-cache, must-revalidate";
    private static final String exMessage = "Fail in getting command from repository";

    @Override
    public void init() throws ServletException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        LogicFactory factory = LogicFactory.getInstance();
        OrderLogic orderLogic = factory.getOrderLogic();
        TourLogic tourLogic = factory.getTourLogic();
        executorService.submit(() -> {
            try {
                orderLogic.runStatusUpdater();
            } catch (LogicException e) {
                Logger logger = LogManager.getLogger(Executor.class);
                logger.error(e.getMessage(), e);
            }
        });
        executorService.submit(() -> {
            try {
                tourLogic.runCurrencyUpdater();
            } catch (LogicException e) {
                Logger logger = LogManager.getLogger(Executor.class);
                logger.error(e.getMessage(), e);
            }
        });
        executorService.shutdown();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName;
        Command executionCommand;
        commandName = request.getParameter(commandKey);
        try {
            executionCommand = provider.getCommand(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ServletException(exMessage, e);
        }
        response.setHeader(cacheControlKey, cacheControlVal);
        executionCommand.execute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
