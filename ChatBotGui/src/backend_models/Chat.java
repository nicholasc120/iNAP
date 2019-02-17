/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

import java.io.*;
import java.util.*;
//import java.util.Scanner;

/**
 *
 * @author cerva
 */
public class Chat {

    ArrayList<String> chatLog = new ArrayList<String>();

    FileWriter fw;
    PrintWriter pw;
    String uselessWords;
    String learnedWords = "";
    String newWord;
    String[] profile;
    String[] words;
    String learnedWordsArray[];
    String name = "dude";
    String prevMessage = "";
    boolean talking = true;
    boolean talkingAboutFeelings;

    public Chat() throws IOException {
        talkingAboutFeelings = true;
        Scanner sc = new Scanner(new File("UselessWords.txt"));
        uselessWords = sc.nextLine();
        sc.close();
        Scanner scc = new Scanner(new File("NewWords.txt"));
        while (scc.hasNext()) {
            learnedWords += scc.nextLine() + "\n";
        }
        scc.close();
        Scanner sc2 = new Scanner(new File("Profile.txt"));
        String temp = "";
        while (sc2.hasNext()) {
            temp += sc2.nextLine() + "\n";
        }
        sc2.close();
        profile = temp.split("\n");
        name = profile[0].substring(5, profile[0].length());

        addToChatLog("INAP: Hey " + name + "! Welcome to INAP, or I Need A Pal! How are you?");
    }

    public String respond(String input) {
        System.out.println("respond");
        learnedWordsArray = learnedWords.split("\n");
        addToChatLog("You: " + input);
        input = input.toLowerCase();
        input = input.replace(",", "");
        input = input.replace(".", "");
        input = input.replace("?", "");
        input = input.replace("!", "");
        words = input.split(" ");
        prevMessage = chatLog.get(chatLog.size() - 2);

        if (talking) {
            if (input.isEmpty() || !input.matches(".*[^ ].*")) {
                if ((int) (Math.random() * 2) == 0) {
                    return "INAP: Come on, this is no fun unless you say something!";
                } else {
                    return "INAP: Staaaahp ignooooring meeee";
                }
            }

            if (talkingAboutFeelings) {
                return talkAboutFeelings(input);
            }
            if (input.contains("feel") || input.contains("am") && ((input.contains("bad")) || input.contains("crap")
                    || (input.contains("don't feel") && input.contains("good")) || input.contains("sad") || input.contains("terrible"))) {
                talkingAboutFeelings = true;
                return "INAP: Is something wrong?";
            }

            if (prevMessage.contains("? I haven't")) {
                input = input.replace("it's a ", "");
                input = input.replace("it's", "");
                input = input.replace(newWord + " is", "");
                input = input.replace(newWord + " are", "");
                input = input.replace("a " + newWord + " is", "");
                input = input.replace("an " + newWord + " is", "");
                if (!input.contains("friend") && !(input.contains("person") && (input.contains("I ") || input.contains("me ") || input.contains("my  "))) && !input.contains("she ") && !input.contains("he ") && !input.contains("they ")) {
                    learnNewWord(newWord, input + " ");
                    return "INAP: I see! That's pretty interesting.";
                } else {
                    return "INAP: Oh I see! Do you like " + newWord + " as a person?";
                }
            }
            if (prevMessage.contains("something you think is fun")) {
                input = input.replace("enjoy", "");
                input = input.replace("i like", "");
                input = input.replace("like to", "");
                input = input.replace("love to", "");
                input = input.replace("love", "");
                input = input.replace("fun", "");
                input = input.replace("is fun", "");
                profile[1] += "/" + input;
                updateProfile();
                return "INAP: " + input + " seems like lots of fun";
            }
            if (prevMessage.contains("something you think isn't any fun")) {
                input = input.replace("don't enjoy", "");
                input = input.replace("i hate", "");
                input = input.replace("i dislike", "");
                input = input.replace("hate to", "");
                input = input.replace("don't like to", "");
                input = input.replace("hate", "");
                input = input.replace("is no fun", "");
                input = input.replace("is boring", "");
                input = input.replace("is lame", "");
                input = input.replace("boring", "");
                input = input.replace("lame", "");
                input = input.replace(" sucks", "");

                profile[2] += "/" + input;
                updateProfile();
                return "INAP: " + input + " seems really lame!";
            }

            if (prevMessage.contains("as a person?")) {
                if (input.contains("yes") || input.contains("yea") || input.contains("yeah") || input.contains("yup") || input.contains("yep") && !input.contains("no") && !input.contains("nah")) {
                    profile[3] += newWord + " ";
                    updateProfile();
                    return "INAP: I guess " + newWord + " is cool then!";
                } else if (input.contains("no") || input.contains("nah") || input.contains("nope") && !input.contains("yes") || !input.contains("yea") || !input.contains("yeah") || !input.contains("yup") || !input.contains("yep")) {
                    profile[4] += newWord + " ";
                    updateProfile();
                    return "INAP: I guess " + newWord + " is no fun then!";
                } else {
                    return "INAP: Hmm... I think I understand";
                }
            }

            if ((input.contains("can") && input.contains("you") && (Math.abs(input.indexOf("can") - input.indexOf("you")) == 4))
                    || (input.contains("will") && input.contains("you") && (Math.abs(input.indexOf("you") - input.indexOf("will")) == 4) || (Math.abs(input.indexOf("you") - input.indexOf("will")) == 5))) {
                //System.out.println("Request being made");
                if (input.contains("call me ")) {
                    // System.out.println("Request accepted");
                    profile[0] = profile[0].replace(name, input.substring(input.indexOf("call me ") + 8, input.length()));
                    name = input.substring(input.indexOf("call me ") + 8, input.length());
                    updateProfile();
                    return "INAP: Alright, I can call you " + input.substring(input.indexOf("call me ") + 8, input.length());
                } else if (input.contains("say ")) {
                    if (input.contains("say my name")) {
                        return "INAP: " + name;

                    }
                    return "INAP: " + input.substring(input.indexOf("say ") + 4, input.length());
                } else {
                    //  System.out.println("Request denied");
                    return "INAP: I don't think I can, dude :/";
                }
            } 
            
            if (input.contains("bye")) {
                if (prevMessage.contains("end the conversation")) {
                    talking = false;
                    return "INAP: Goodbye " + name + "! I enjoyed talking to you! ^_^";
                } else {
                    return "INAP: Say bye again if you want to end the conversation :)";
                }
            } 
            if (input.contains("hi") || input.contains("hello") || input.contains("greetings") || input.contains("hey")) {
                for (int i = 0; i < words.length; i++) {
                    if (words[i].equals("hi") || words[i].equals("hello") || words[i].equals("greetings") || words[i].equals("hey")) {
                        return "INAP: Hey there :)";
                    }

                }
                return normalConversation(input);
            } else {
                //normal conversation
                return normalConversation(input);
                //if didn't learn a new word
            }

        } else {
            return "INAP has left. Please close the window.";
        }
    }

    public String talkAboutFeelings(String input) {
        System.out.println("Talking About feelings");
        if (((input.contains("bad") && (!input.contains("not"))) || input.contains("depress") || (input.contains("not") && input.contains("good"))
                || (input.contains("sad") && !input.contains("not"))) && !prevMessage.contains("What's wrong?") || input.contains("yes") || input.contains("yea")) {
            return "INAP: " + name + ", What's wrong? You can talk to me";
        }

        if ((input.contains("good") && (!input.contains("not"))) || (input.contains("not") && input.contains("bad"))
                || input.contains("great") || input.contains("fine") || input.contains("no")) {
            talkingAboutFeelings = false;
            return "INAP: That's good :)";
        }

        if (prevMessage.contains("What's wrong? You can talk to me")) {
            return "INAP: Tell me more!";
        }
        if (prevMessage.contains("INAP: Tell me more!")) {
            talkingAboutFeelings = false;
            return "INAP: I'm sorry to hear that :( I wish I could help but I'm just an AI";
        } else {
            talkingAboutFeelings = false;
            return normalConversation(input);
        }
    }

    public String normalConversation(String input) {
        System.out.println("Normal Conversation");
        for (int count = 0; count < words.length; count++) {

            if (newWord(words[count])) {
                newWord = words[count].toLowerCase();
                if (count > 0) {
                    if (words[count - 1].equals("a")) {
                        return "INAP: What's a " + words[count] + "? I haven't ever heard of it before";
                    }
                }
                if (newWord.lastIndexOf("s") == newWord.length() - 1) {
                    return "INAP: What are " + newWord + "? I haven't ever heard of them before";
                }
                if (!words[count].startsWith("a") && !words[count].startsWith("e") && !words[count].startsWith("i") && !words[count].startsWith("o") && !words[count].startsWith("u")) {
                    return "INAP: What's a " + words[count] + "? I haven't ever heard of it before";
                } else {
                    return "INAP: What's an " + words[count] + "? I haven't heard of it before";
                }
            }

            if (recognizeName(words[count])) {
                if (profile[3].contains(words[count])) {
                    return "INAP: How is " + words[count] + " doing? I hope they're doing alright!";
                } else {
                    return "INAP: From what I've heard, I don't like " + words[count] + " very much :\\";
                }
            }

            if (learnedWords.contains(words[count] + ":")) {
                for (int ii = 0; ii < learnedWordsArray.length; ii++) {
                    if (learnedWordsArray[ii].startsWith(words[count])) {
                        if (learnedWordsArray[ii].contains("sport")) {
                            return "INAP: " + words[count] + " seems like a pretty awesome sport";
                        }
                        if (learnedWordsArray[ii].contains("pet")) {
                            return "INAP: a " + words[count] + " seems like a pretty cool pet to have";
                        }
                        if (learnedWordsArray[ii].contains("book") || learnedWordsArray[ii].contains("novel")) {
                            return "INAP: " + words[count] + " sounds really interesting!";
                        }
                        if (learnedWordsArray[ii].contains("food") || learnedWordsArray[ii].contains("meal")) {
                            return "INAP: I'd like to taste " + words[count] + " one day. But I can't. I'm an AI";
                        }
                        if (learnedWordsArray[ii].contains("anime") || learnedWordsArray[ii].contains("cartoon") || learnedWordsArray[ii].contains("show") || learnedWordsArray[ii].contains("movie") || learnedWordsArray[ii].contains("film")) {
                            return words[count] + " sounds really exciting!";
                        }
                        return "INAP: Hey I know what a " + words[count] + " is! It's " + learnedWordsArray[ii].substring(learnedWordsArray[ii].indexOf(":") + 1, learnedWordsArray[ii].length());
                    }
                }
            }
        }

        return saySomethingRandom();
    }

    public String saySomethingRandom() {
        System.out.println("Say Something Random");
        int i = (int) (4 * Math.random());
        if (i == 0) {
            return "INAP: I don't get it";
        }
        if (i == 1) {
            return "INAP: Tell me more";
        }
        if (i == 2) {
            //ask question
            int questionToAsk = (int) (2 * Math.random());
            if (questionToAsk == 1) {
                //ask about interests
                return "INAP: Hey " + name + ", tell you something you think is fun ^-^";
            } else {
                //ask about dislikes 
                return "INAP: Hey " + name + ", tell me something you think isn't any fun ;P";
            }

        } else {
            return "INAP: Go on";
        }
    }

    public String[] getChatLog() {
        String[] returnArray = new String[chatLog.size()];
        for (int i = 0; i < chatLog.size(); i++) {
            returnArray[i] = chatLog.get(i);
        }
        return returnArray;
    }

    public void addToChatLog(String s) {
        chatLog.add(s);
    }

    public boolean newWord(String word) {
        String a = word.toLowerCase();
        return !uselessWords.contains(" " + a + " ") && !learnedWords.contains(a + ":") && !recognizeName(a);
    }

    public boolean recognizeName(String name) {
        String a = " " + name.toLowerCase() + " ";
        return (profile[3].contains(a) || profile[4].contains(a));
    }

    public void learnNewWord(String word, String definition) {
        try {
            fw = new FileWriter("NewWords.txt");
            pw = new PrintWriter(fw);
            definition = definition.replace("my", "your");
            definition = definition.replace("I ", "you");

            learnedWords += word.toLowerCase() + ":" + definition.toLowerCase() + "\n";
            pw.println(learnedWords);
            pw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void updateProfile() {
        try {
            FileWriter fw = new FileWriter("profile.txt");
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < profile.length; i++) {
                pw.println(profile[i]);
            }
            pw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println();
        }
    }

}
