package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        var account = accounts.get(id);
        return account == null ? Optional.empty() : Optional.of(account);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        var result = false;
        var srcAccount = getById(fromId).orElse(null);
        var dstAccount = getById(toId).orElse(null);
        if (srcAccount != null && dstAccount != null
                && srcAccount.amount() >= amount) {
            update(new Account(srcAccount.id(), srcAccount.amount() - amount));
            update(new Account(dstAccount.id(), dstAccount.amount() + amount));
            result = true;
        }
        return result;
    }
}
