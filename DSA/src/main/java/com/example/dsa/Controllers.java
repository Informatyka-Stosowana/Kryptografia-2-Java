//DSA
//Copyright(C) 2023
//Dominik Kwiecień 242448
//Hubert Pietras 242494

//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.

//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.example.dsa;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


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

    @FXML
    protected void onClickReadFileGenerate() {
        MessageGenerate.setText(readFile());
    }

    @FXML
    protected void onClickReadFileVerify() {
        MessageVerify.setText(readFile());
    }

    private String readFile()
    {
        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(null);
        byte[] byteArray = null;

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            FileInputStream inputStream = null;

            try {
                inputStream = new FileInputStream(file);
                byteArray = new byte[(int) file.length()];
                inputStream.read(byteArray);
            } catch (Exception e) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception ignored) {}
                }
                return "Błąd wczytywania pliku";
            }
        }

        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : byteArray) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }
}