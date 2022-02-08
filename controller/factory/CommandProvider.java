package by.epamtc.stanislavmelnikov.controller.factory;

import by.epamtc.stanislavmelnikov.controller.commandimpl.addrole.AddUserRole;
import by.epamtc.stanislavmelnikov.controller.commandimpl.changepersonal.ChangePersonalData;
import by.epamtc.stanislavmelnikov.controller.commandenum.CommandName;
import by.epamtc.stanislavmelnikov.controller.commandimpl.*;
import by.epamtc.stanislavmelnikov.controller.commandimpl.addfeedback.AddFeedback;
import by.epamtc.stanislavmelnikov.controller.commandimpl.addhotel.AddHotel;
import by.epamtc.stanislavmelnikov.controller.commandimpl.addorder.AddOrder;
import by.epamtc.stanislavmelnikov.controller.commandimpl.addtour.AddTour;
import by.epamtc.stanislavmelnikov.controller.commandimpl.adduser.AddUser;
import by.epamtc.stanislavmelnikov.controller.commandimpl.changehotel.ChangeHotel;
import by.epamtc.stanislavmelnikov.controller.commandimpl.giveadminrights.GiveAdminRights;
import by.epamtc.stanislavmelnikov.controller.commandimpl.signin.SignIn;
import by.epamtc.stanislavmelnikov.controller.commandimpl.updateuser.UpdateUser;
import by.epamtc.stanislavmelnikov.controller.commandimpl.changetour.ChangeTour;
import by.epamtc.stanislavmelnikov.controller.commandimpl.fullorderinfo.FullOrderInfo;
import by.epamtc.stanislavmelnikov.controller.commandimpl.hotelmanager.HotelManager;
import by.epamtc.stanislavmelnikov.controller.commandimpl.registration.Registration;
import by.epamtc.stanislavmelnikov.controller.commandimpl.removefbk.RemoveFeedback;
import by.epamtc.stanislavmelnikov.controller.commandimpl.removehotel.RemoveHotel;
import by.epamtc.stanislavmelnikov.controller.commandimpl.removeorder.RemoveOrder;
import by.epamtc.stanislavmelnikov.controller.commandimpl.removetour.RemoveTour;
import by.epamtc.stanislavmelnikov.controller.commandimpl.removeuser.RemoveUser;
import by.epamtc.stanislavmelnikov.controller.commandimpl.resetpassword.ResetPassword;
import by.epamtc.stanislavmelnikov.controller.commandimpl.signout.SignOut;
import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<CommandName, Command> repository = new HashMap<>();

    public CommandProvider() {
        repository.put(CommandName.HOME, new HomeManager());
        repository.put(CommandName.TOURS, new TourManager());
        repository.put(CommandName.SIGN_IN, new SignIn());
        repository.put(CommandName.SIGN_OUT, new SignOut());
        repository.put(CommandName.REGISTRATION, new Registration());
        repository.put(CommandName.FULL_TOUR_INFO, new FullTourInfo());
        repository.put(CommandName.ADD_TOUR, new AddTour());
        repository.put(CommandName.REMOVE_TOUR, new RemoveTour());
        repository.put(CommandName.CHANGE_TOUR, new ChangeTour());
        repository.put(CommandName.HOTELS, new HotelManager());
        repository.put(CommandName.ADD_HOTEL, new AddHotel());
        repository.put(CommandName.FULL_HOTEL_INFO, new FullHotelInfo());
        repository.put(CommandName.REMOVE_HOTEL, new RemoveHotel());
        repository.put(CommandName.CHANGE_HOTEL, new ChangeHotel());
        repository.put(CommandName.ADD_ORDER, new AddOrder());
        repository.put(CommandName.ORDERS, new OrderManager());
        repository.put(CommandName.FULL_ORDER_INFO, new FullOrderInfo());
        repository.put(CommandName.REMOVE_ORDER, new RemoveOrder());
        repository.put(CommandName.ADD_FEEDBACK, new AddFeedback());
        repository.put(CommandName.FEEDBACKS, new FeedbackManager());
        repository.put(CommandName.REMOVE_FEEDBACK, new RemoveFeedback());
        repository.put(CommandName.PERSONAL_DATA, new PersonalData());
        repository.put(CommandName.UPDATE_USER, new UpdateUser());
        repository.put(CommandName.USERS, new UserManager());
        repository.put(CommandName.FULL_USER_INFO, new FullUserInfo());
        repository.put(CommandName.ADD_USER, new AddUser());
        repository.put(CommandName.REMOVE_USER, new RemoveUser());
        repository.put(CommandName.RESORTS, new ResortAjax());
        repository.put(CommandName.LOCATION_AJAX, new LocationAjax());
        repository.put(CommandName.RESET_PASSWORD, new ResetPassword());
        repository.put(CommandName.CHANGE_PERSONAL_DATA, new ChangePersonalData());
        repository.put(CommandName.ADD_NEW_ROLE, new AddUserRole());
        repository.put(CommandName.GIVE_ADMIN_RIGHTS, new GiveAdminRights());
        repository.put(CommandName.BLOCK_USER, new BlockUser());
    }

    public void setCommand(CommandName commandName, Command command) {
        repository.put(commandName, command);
    }

    public Command getCommand(String name) throws IllegalArgumentException, NullPointerException {
        CommandName commandName;
        Command command;
        commandName = CommandName.valueOf(name.toUpperCase());
        command = repository.get(commandName);
        return command;
    }
}
