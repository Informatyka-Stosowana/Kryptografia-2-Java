package com.example.dsa;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public class Controllers {
    @FXML
    private TextArea HInput;

    @FXML
    private TextArea HOutput;

    @FXML
    private TextArea MessageGenerate;

    @FXML
    private TextArea MessageVerify;

    @FXML
    private TextArea PInput;

    @FXML
    private TextArea POutput;

    @FXML
    private TextArea PublicKeyInput;

    @FXML
    private TextArea PublicKeyOutput;

    @FXML
    private TextArea QInput;

    @FXML
    private TextArea QOutput;

    @FXML
    private TextArea S1Input;

    @FXML
    private TextArea S1Output;

    @FXML
    private TextArea S2Input;

    @FXML
    private TextArea S2Output;

    @FXML
    private Label VerifyLabel;


    @FXML
    protected void onClickGenerateSignature() throws NoSuchAlgorithmException {
        Sign sign = new Sign();
        byte[] message = MessageGenerate.getText().getBytes();
        BigInteger[] signature = sign.SignMessage(message);
        S1Output.setText(signature[0].toString());
        S2Output.setText(signature[1].toString());
        POutput.setText(sign.getP().toString());
        QOutput.setText(sign.getQ().toString());
        HOutput.setText(sign.getH().toString());
        PublicKeyOutput.setText(sign.getPublicKey().toString());
    }

    @FXML
    protected void onClickVerifySignature() throws NoSuchAlgorithmException {
        Verify verify = new Verify();
        byte[] message = MessageVerify.getText().getBytes();

        BigInteger s1, s2, p, q, h, publicKey;

        try {
            s1 = new BigInteger(S1Input.getText());
        } catch (NumberFormatException e) {
            S1Input.setText("Wprowadzono niepoprawną wartość!");
            return;
        }

        try {
            s2 = new BigInteger(S2Input.getText());
        } catch (NumberFormatException e) {
            S2Input.setText("Wprowadzono niepoprawną wartość!");
            return;
        }

        try {
            p = new BigInteger(PInput.getText());
        } catch (NumberFormatException e) {
            PInput.setText("Wprowadzono niepoprawną wartość!");
            return;
        }

        try {
            q = new BigInteger(QInput.getText());
        } catch (NumberFormatException e) {
            QInput.setText("Wprowadzono niepoprawną wartość!");
            return;
        }

        try {
            h = new BigInteger(HInput.getText());
        } catch (NumberFormatException e) {
            HInput.setText("Wprowadzono niepoprawną wartość!");
            return;
        }

        try {
            publicKey = new BigInteger(PublicKeyInput.getText());
        } catch (NumberFormatException e) {
            PublicKeyInput.setText("Wprowadzono niepoprawną wartość!");
            return;
        }

        if (verify.VerifyMessage(message, s1, s2, p, q, h, publicKey)) {
            VerifyLabel.setText("Podpis poprawny");
        }
        else {
            VerifyLabel.setText("Podpis niepoprawny");
        }
    }
}