package com.machine;

import com.helpers.loaders.ConfirmationLoader;
import com.helpers.loaders.NoteLoader;
import com.helpers.loaders.ProductLoader;
import com.helpers.records.Confirmation;
import com.helpers.records.Note;
import com.helpers.records.Product;
import com.interfaces.WindowHandler;
import com.interfaces.WindowSingleHandler;
import com.utils.Option;
import com.utils.Screen;
import com.utils.Window;

public class Machine {
    private final Fund fund;
    private final Promotion promotion;
    private final Option<Note> noteOption;
    private final Option<Product> productOption;
    private final Option<Confirmation> confirmationOption;

    public Machine() {
        fund = new Fund(0);
        noteOption = new Option<>(NoteLoader.getLoader().load(), "Please insert your note:", false);
        productOption = new Option<>(ProductLoader.getLoader().load(), "Please pick product:");
        confirmationOption = new Option<>(ConfirmationLoader.getLoader().load(), "Continue?", false);
        promotion = new Promotion();
    }


    public void start() {
        boolean isContinue = true;
        while (isContinue){
            addFund();
            isContinue = confirmContinue();
        }
        Screen.writeln("Have a nice day!");
    }

    private void printFundInfo() {
        Screen.writeln("Current funds: " + fund.getTotal());
    }

    private boolean confirmContinue() {
        final boolean[] isContinue = {false};
        Window<Confirmation> window = new Window<>(confirmationOption, false);
        window.showOnce(new WindowSingleHandler<Confirmation>() {
            @Override
            public void onInput(Confirmation confirmation) {
                if (confirmation.key().equals("yes")) {
                    isContinue[0] = true;
                }
            }

            @Override
            public void beforeInput() {
            }

            @Override
            public void onCancel() {
            }
        });
        return isContinue[0];
    }

    private void addFund() {
        Window<Note> window = new Window<>(this.noteOption);
        window.showOnce(new WindowSingleHandler<Note>() {
            @Override
            public void beforeInput() {
            }

            @Override
            public void onInput(Note note) {
                fund.add(note.amount());
                pickProduct();
            }

            @Override
            public void onCancel() {
            }
        });
    }

    public void pickProduct() {
        Window<Product> window = new Window<>(productOption);
        String[] errors = {null};
        window.show(new WindowHandler<Product>() {
            @Override
            public void beforeInput() {
                printFundInfo();
                if(errors[0] != null){
                    Screen.writeln(errors[0]);
                    errors[0] = null;
                }
            }

            @Override
            public boolean onInput(Product product) {
                if (product.price() > fund.getTotal()) {
                    errors[0] = "Not enough fund to purchase!";
                    return true;
                }
                releaseProduct(product);
                promotion.apply(product);
                releaseChange();
                return false;
            }

            @Override
            public void onCancel() {
                refund();
            }
        });
    }

    private void refund() {
        if (fund.getTotal() > 0) {
            Screen.clear();
            Screen.writeln("Please take your refund: " + fund.refund());
        }
    }

    private void releaseChange() {
        if (fund.getTotal() > 0) {
            Screen.writeln("Please take your change: " + fund.refund());
        }
    }

    private void releaseProduct(Product product) {
        Screen.clear();
        Screen.writeln("Releasing " + product.label() + "...");
        Screen.writeln("Done! Enjoy your drink!");
        fund.debit(product.price());
    }
}
