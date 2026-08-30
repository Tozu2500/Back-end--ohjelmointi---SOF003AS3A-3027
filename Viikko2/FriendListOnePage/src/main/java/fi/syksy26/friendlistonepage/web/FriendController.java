package fi.syksy26.friendlistonepage.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fi.syksy26.friendlistonepage.domain.Friend;

@Controller
public class FriendController {

    private final List<Friend> friends = new ArrayList<>();

    public FriendController() {
        friends.add(new Friend("Kate", "Cole"));
        friends.add(new Friend("Dan", "Brown"));
        friends.add(new Friend("Mike", "Mars"));
    }

    // Shows the friend list and the "Add new friend" form on the same page
    @GetMapping("/friendlist")
    public String showFriends(Model model) {
        model.addAttribute("friends", friends);
        model.addAttribute("friend", new Friend());
        return "friendlist"; // friendlist.html
    }

    // Saves the new friend information and shows the updated list
    @PostMapping("/friendlist")
    public String saveFriend(@ModelAttribute Friend friend) {
        friends.add(friend);
        return "redirect:/friendlist";
    }

}
