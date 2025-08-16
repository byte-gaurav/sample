package com.lowlevel.design.lld.splitwise;

import java.util.*;

public class Splitwise {

    private Map<String, User> users = new HashMap<>();
    private Map<String, Group> groups = new HashMap<>();
    private Map<String, BalanceSheet> balanceSheets = new HashMap<>();

    public void addUser(String id, String name) {
        users.put(id, new User(id, name));
        balanceSheets.put(id, new BalanceSheet(id));
    }

    public void addGroup(String groupId, String groupName, List<String> userIds) {
        List<User> groupUsers = new ArrayList<>();
        for (String userId : userIds) {
            if (users.containsKey(userId)) {
                groupUsers.add(users.get(userId));
            }
        }
        groups.put(groupId, new Group(groupId, groupName, groupUsers));
    }

    public void addExpense(String expenseId, double amount, String paidById, List<String> splitAmongIds) {
        User paidBy = users.get(paidById);
        List<User> splitAmong = new ArrayList<>();
        for (String userId : splitAmongIds) {
            splitAmong.add(users.get(userId));
        }
        Expense expense = new Expense(expenseId, amount, paidBy, splitAmong);
        double splitAmount = amount / splitAmong.size();

        for (User user : splitAmong) {
            if (!user.getId().equals(paidById)) {
                balanceSheets.get(user.getId()).addBalance(paidById, -splitAmount);
                balanceSheets.get(paidById).addBalance(user.getId(), splitAmount);
            }
        }
    }

    public void simplifyBalances() {
        // Floyd-Warshall-like approach for all-pairs debt simplification
        List<String> userIds = new ArrayList<>(users.keySet());
        int n = userIds.size();

        // For each possible intermediate user k
        for (int k = 0; k < n; k++) {
            String mid = userIds.get(k);
            for (int i = 0; i < n; i++) {
                String from = userIds.get(i);
                if (from.equals(mid)) continue;
                for (int j = 0; j < n; j++) {
                    String to = userIds.get(j);
                    if (to.equals(mid) || to.equals(from)) continue;

                    double fromToMid = balanceSheets.get(from).getBalances().getOrDefault(mid, 0.0);
                    double midToTo = balanceSheets.get(mid).getBalances().getOrDefault(to, 0.0);

                    // Only transfer if from owes mid and mid owes to
                    if (fromToMid < 0 && midToTo < 0) {
                        double transfer = Math.min(-fromToMid, -midToTo);
                        if (transfer > 0) {
                            balanceSheets.get(from).addBalance(mid, transfer); // reduce from->mid
                            balanceSheets.get(mid).addBalance(to, transfer);   // reduce mid->to
                            balanceSheets.get(from).addBalance(to, -transfer); // add from->to
                        }
                    }
                }
            }
        }
        // Clean up near-zero balances
        for (BalanceSheet sheet : balanceSheets.values()) {
            sheet.getBalances().entrySet().removeIf(e -> Math.abs(e.getValue()) < 1e-6);
        }
    }

    public Map<String, Double> getUserBalances(String userId) {
        return balanceSheets.get(userId).getBalances();
    }

    public class User {
        private String id;
        private String name;

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class Expense {
        private String id;
        private double amount;
        private User paidBy;
        private List<User> splitAmong;

        public Expense(String id, double amount, User paidBy, List<User> splitAmong) {
            this.id = id;
            this.amount = amount;
            this.paidBy = paidBy;
            this.splitAmong = splitAmong;
        }

        public String getId() {
            return id;
        }

        public double getAmount() {
            return amount;
        }

        public User getPaidBy() {
            return paidBy;
        }

        public List<User> getSplitAmong() {
            return splitAmong;
        }
    }

    public class Group {
        private String id;
        private String name;
        private List<User> users;

        public Group(String id, String name, List<User> users) {
            this.id = id;
            this.name = name;
            this.users = users;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<User> getUsers() {
            return users;
        }
    }

    public class BalanceSheet {
        private String userId;
        private Map<String, Double> balances = new HashMap<>();

        public BalanceSheet(String userId) {
            this.userId = userId;
        }

        public void addBalance(String otherUserId, double amount) {
            balances.put(otherUserId, balances.getOrDefault(otherUserId, 0.0) + amount);
        }

        public Map<String, Double> getBalances() {
            return balances;
        }
    }
}


