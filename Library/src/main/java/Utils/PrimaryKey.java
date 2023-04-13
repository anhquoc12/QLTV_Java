/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author dell
 */
public class PrimaryKey {

    public PrimaryKey() {
    }

    public String ID_4(String key, String lastkey) {
        if (lastkey == null) {
            return key + "0001";
        }
        int num = Integer.parseInt(lastkey.substring(2, 6)) + 1;
        return key + "0000".substring(0, 6 - 2 - Integer.toString(num).length()) + num;
    }

    public String ID_8(String key, String lastkey) {
        if (lastkey == null) {
            return key + "00000001";
        }
        int num = Integer.parseInt(lastkey.substring(2, 10)) + 1;
        return key + "00000000".substring(0, 10 - 2 - Integer.toString(num).length()) + num;
    }

    public static void main(String[] args) {
        System.out.print(new PrimaryKey().ID_8("CTPM", "CTPM00000099"));
    }
}
