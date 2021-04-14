package com.example.MqttSubscriber.user.web;

import com.example.MqttSubscriber.user.model.MQTTBrokerConfig;
import com.example.MqttSubscriber.user.model.User;
import com.example.MqttSubscriber.user.service.MQTTSubscriber;
import com.example.MqttSubscriber.user.service.SecurityService;
import com.example.MqttSubscriber.user.service.UserService;
import com.example.MqttSubscriber.user.validator.UserValidator;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        //securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }



    @Autowired
    private MQTTSubscriber sub;

    private boolean connected;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserController.class);

    //    @RequestMapping(value = "/home", method = RequestMethod.GET)
//    @GetMapping({"/", "/home"})
    @GetMapping("/home")
    public String index(MQTTBrokerConfig brokerConfig, Model model) {
        LOGGER.info("Serving home page");
        if (this.connected) {
            LOGGER.info("Already connected");
            model.addAttribute("connectionStatus", getConnectionStatus());
            return "redirect:/showmessages";
        } else {
            model.addAttribute("connectionStatus", getConnectionStatus());
            model.addAttribute("brokerConfig", new MQTTBrokerConfig());
            return "home";
        }
    }

    private String getConnectionStatus() {
        String connectionString = "Not connected";
        if (this.connected) {
            connectionString = "Connected to " + this.sub.getConnectionString() + "/" + this.sub.getTopic();
        }
        LOGGER.debug("Connection status {}", connectionString);
        return connectionString;
    }

    @PostMapping("/home")
    public String connectToBroker(@Valid MQTTBrokerConfig brokerConfig, BindingResult bindingResult, Model model) {
        LOGGER.info("Connecting to broker");
        if (bindingResult.hasErrors()) {
            return "home";
        }
        try {
            this.sub.connect(brokerConfig);
            this.connected = true;
        } catch (MqttException e) {
            LOGGER.error("error when connecting to broker", e);
            this.connected = false;
        }
        model.addAttribute("connectionStatus", getConnectionStatus());
        return "redirect:/showmessages";
    }

    @GetMapping({"/","/showmessages"})
    public String showMessages(Model model) {
        LOGGER.info("Serving show messages page");
        model.addAttribute("connectionStatus", getConnectionStatus());
        model.addAttribute("messages", this.sub.messages());
        return "showmessages";
    }

    @PostMapping({"/","/showmessages"})
    public String disconnect(Model model) {
        LOGGER.info("Disconnecting from broker");
        this.sub.disconnect();
        this.connected = false;
        model.addAttribute("connectionStatus", getConnectionStatus());
        model.addAttribute("brokerConfig", new MQTTBrokerConfig());
        return "redirect:/home";
    }
}