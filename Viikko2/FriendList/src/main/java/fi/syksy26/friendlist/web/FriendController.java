package fi.syksy26.friendlist.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fi.syksy26.friendlist.domain.Friend;

@Controller
public class FriendController {

    private final List<Friend> friends = new ArrayList<>();

    public FriendController() {
        friends.add(new Friend("Kate", "Cole"));
        friends.add(new Friend("Dan", "Brown"));
        friends.add(new Friend("Mike", "Mars"));
    }

    // Shows the friend list
    @GetMapping("/friendlist")
    public String showFriends(Model model) {
        model.addAttribute("friends", friends);
        return "friendlist"; // friendlist.html
    }

    // Opens the "Add new friend" page
    @GetMapping("/add")
    public String addFriendForm(Model model) {
        model.addAttribute("friend", new Friend());
        return "addfriend"; // addfriend.html
    }

    // Saves the new friend information
    @PostMapping("/save")
    public String saveFriend(@ModelAttribute Friend friend) {
        friends.add(friend);
        return "redirect:/friendlist";
    }

}
