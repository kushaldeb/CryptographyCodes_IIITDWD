import java.util.*;
import java.lang.*;
import java.util.Scanner;

class CaesarCipher{
	String plainTxt;

	CaesarCipher(String text){
		plainTxt = text;
	}

	public StringBuffer encrypt(int key){
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < plainTxt.length(); i++){
			if(Character.isUpperCase(plainTxt.charAt(i))){
				char ch = (char)(((int)plainTxt.charAt(i) + key - 65)%26 + 65);
				result.append(ch);
			}
			else{
				char ch = (char)(((int)plainTxt.charAt(i) + key - 97)%26 + 97);
				result.append(ch);
			}
		}
		return result;
	}

	public StringBuffer decrypt(int key, StringBuffer cipher){
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < cipher.length(); i++){
			int num;
			if(Character.isUpperCase(cipher.charAt(i))){
				num = (((int)cipher.charAt(i) - key - 65)%26 + 65);
				if(num < 65)
					num += 26;
				char ch = (char)num;
				result.append(ch);
			}
			else{
				num = (((int)cipher.charAt(i) - key - 97)%26 + 97);
				if(num < 97)
					num += 26;
				char ch = (char)num;
				result.append(ch);
			}
		}
		return result;
	}
}

class MonoAlphabeticCipher{
	public static char letters[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',

            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',

            'w', 'x', 'y', 'z' };

    public char keyArray[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',

            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',

            'W', 'X', 'Y', 'Z' };

    public char[] ramdomiseArray(char[] keyArray){
    	Random rgen = new Random();
    	for(int i = 0; i < keyArray.length; i++){
    		int randomPosition = rgen.nextInt(keyArray.length);
    		char temp = keyArray[i];
    		keyArray[i] = keyArray[randomPosition];
    		keyArray[randomPosition] = temp;
    	}
    	return keyArray;
    }

    public String encrypt(String plainTxt){
    	keyArray = ramdomiseArray(keyArray);
    	char cipher[] = new char[(plainTxt.length())];
    	
    	for(int i = 0; i < plainTxt.length(); i++){
    		for(int j = 0; j < 26; j++){
    			if(letters[j] == plainTxt.charAt(i)){
    				cipher[i] = keyArray[j];
    				break;
    			}
    		}
    	}
    	System.out.println("Key : " + (new String(keyArray)));
    	return (new String(cipher));
    }

    public String decrypt(String cipher){
    	char decrypted[] = new char[(cipher.length())];
    	for(int i = 0; i < cipher.length(); i++){
    		for(int j = 0; j < 26; j++){
    			if(keyArray[j] == cipher.charAt(i)){
    				decrypted[i] = letters[j];
    				break;
    			}    			
    		}    		
    	}
    	return (new String(decrypted));
    }
}

class PlayFairCipher{

	private String keyword = new String();
	private String key = new String();
	private char matrix_arr[][] = new char[5][5];

	public void setKey(String k){
		String K_adjust = new String();
		boolean flag = false;
		K_adjust = K_adjust + k.charAt(0);

		for(int i = 1; i < k.length(); i++){
			for(int j = 0; j < K_adjust.length(); j++){
				if(k.charAt(i) == K_adjust.charAt(j)){
					flag = true;
				}
			}
			if(flag == false)
				K_adjust = K_adjust + k.charAt(i);
			flag = false;
		}
		keyword = K_adjust;
	}

	public void keyGen(){
		boolean flag = true;
		char current;
		key = keyword;
		for(int i = 0; i < 26; i++){
			current = (char)(i + 97);
			if(current == 'j')
				continue;
			for(int j = 0; j < keyword.length(); j++){
				if(current == keyword.charAt(j)){
					flag = false;
					break;
				}
			}
			if(flag)
				key = key + current;
			flag = true;
		}
		System.out.println(key);
		matrix();
	}

	private void matrix(){
		int counter = 0;
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				matrix_arr[i][j] = key.charAt(counter);
				System.out.print(matrix_arr[i][j] + " ");
				counter++;
			}
			System.out.println();
		}
	}

	private String format(String old_text){
		int i = 0;
		int len = 0;
		String text = new String();
		len = old_text.length();

		if(len%2 != 0){
			old_text = old_text + 'x';
			len = old_text.length();
		}

		for(int tmp = 0; tmp < len; tmp++){
			if(old_text.charAt(tmp) == 'j'){
				text = text + 'i';
			}
			else{
				text = text + old_text.charAt(tmp);
			}
		}
		len = text.length();

		for(i = 0; i < len; i += 2){
			if(text.charAt(i+1) == text.charAt(i)){
				text = text.substring(0, i + 1) + 'x' + text.substring(i+1);
			}
		}
		return text;
	}

	private String[] DivideInPairs(String new_string){
		String original = format(new_string);
		int size = original.length();
		if(size%2 != 0){
			size++;
			original = original + 'x';
		}
		String x[] = new String[size/2];
		int counter = 0;

		for(int i = 0; i < size/2; i++){
			x[i] = original.substring(counter, counter + 2);
			counter += 2;
		}
		return x;
	}

	public int[] GetDimensions(char letter){
		int[] key = new int[2];
		if(letter == 'j')
			letter = 'i';

		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				if(matrix_arr[i][j] == letter){
					key[0] = i;
					key[1] = j;
					break;
				}
			}
		}
		return key;
	}

	public String encrypt(String Source){
		String src_arr[] = DivideInPairs(Source);
		String Code = new String();
		char one;
		char two;
		int part1[] = new int[2];
		int part2[] = new int[2];

		for(int i = 0; i < src_arr.length; i++){
			one = src_arr[i].charAt(0);
			two = src_arr[i].charAt(1);
			part1 = GetDimensions(one);
			part2 = GetDimensions(two);
			if(part1[0] == part2[0]){
				if(part1[1] < 4)
					part1[1]++;
				else
					part1[1] = 0;

				if(part2[1] < 4)
					part2[1]++;
				else
					part2[1] = 0;
			}
			else if(part1[1] == part2[1]){
				if(part1[0] < 4)
					part1[0]++;
				else
					part1[0] = 0;
				if(part2[0] < 4)
					part2[0]++;
				else
					part2[0] = 0;
			}
			else{
				int temp = part1[1];
				part1[1] = part2[1];
				part2[1] = temp;
			}
			Code = Code + matrix_arr[part1[0]][part1[1]] + matrix_arr[part2[0]][part2[1]];
		}
		return Code;
	}
}

class PolyAlphabeticCipher{
	String plainTxt;

	PolyAlphabeticCipher(String text){
		plainTxt = text;
	}

	public StringBuffer encrypt(String key){
		int keyNumber;
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < plainTxt.length(); i++){
			if(Character.isUpperCase(key.charAt(i))){
				keyNumber = (int)key.charAt(i) - 65;
			}
			else{
				keyNumber = (int)key.charAt(i) - 97;
			}

			if(Character.isUpperCase(plainTxt.charAt(i))){
				char ch = (char)(((int)plainTxt.charAt(i) + keyNumber - 65)%26 + 65);
				result.append(ch);
			}
			else{
				char ch = (char)(((int)plainTxt.charAt(i) + keyNumber - 97)%26 + 97);
				result.append(ch);				
			}
		}
		return result;
	}

	public StringBuffer decrypt(String key, StringBuffer cipher){
		StringBuffer result = new StringBuffer();
		int keyNumber, num;
		for(int i = 0; i < cipher.length(); i++){
			if(Character.isUpperCase(key.charAt(i))){
				keyNumber = (int)key.charAt(i) - 65;
			}
			else{
				keyNumber = (int)key.charAt(i) - 97;
			}

			if(Character.isUpperCase(cipher.charAt(i))){
				num = (((int)cipher.charAt(i) - keyNumber - 65)%26 + 65);
				if(num < 65)
					num += 26;
				char ch = (char)num;
				result.append(ch);
			}
			else{
				num = (((int)cipher.charAt(i) - keyNumber - 97)%26 + 97);
				if(num < 97)
					num += 26;
				char ch = (char)num;
				result.append(ch);
			}
		}
		return result;
	}
}

class OneTimePadCipher{
	String plainTxt;

	OneTimePadCipher(String text){
		plainTxt = text;
	}

	public StringBuffer encrypt(String key){
		StringBuffer result = new StringBuffer();
		int keyNumber;

		for(int i = 0; i < plainTxt.length(); i++){
			if(Character.isUpperCase(key.charAt(i))){
				keyNumber = (int)key.charAt(i) - 65;
			}
			else{
				keyNumber = (int)key.charAt(i) - 97;
			}

			if(Character.isUpperCase(plainTxt.charAt(i))){
				char ch = (char)(((int)plainTxt.charAt(i) + keyNumber - 65)%26 + 65);
				result.append(ch);
			}
			else{
				char ch = (char)(((int)plainTxt.charAt(i) + keyNumber - 97)%26 + 97);
				result.append(ch);
			}
		}
		return result;
	}

	public StringBuffer decrypt(String key, StringBuffer cipher){
		StringBuffer result = new StringBuffer();
		int keyNumber, num;

		for(int i = 0; i < cipher.length(); i++){
			if(Character.isUpperCase(key.charAt(i))){
				keyNumber = (int)key.charAt(i) - 65;
			}
			else{
				keyNumber = (int)key.charAt(i) - 97;
			}

			if(Character.isUpperCase(cipher.charAt(i))){
				num = (((int)cipher.charAt(i) - keyNumber - 65)%26 + 65);
				if(num < 65)
					num += 26;
				char ch = (char)num;
				result.append(ch);
			}
			else{
				num = (((int)cipher.charAt(i) - keyNumber - 97)%26 + 97);
				if(num < 97)
					num += 26;
				char ch = (char)num;
				result.append(ch);
			}
		}
		return result;
	}
}

public class Assignment1{
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("1. Caesar Cipher\n2. MonoAlphabeticCipher\n3. Playfair Cipher\n4. Polyalphabetic cipher\n5. One Time Pad");
		System.out.println("6. Exit\n");

		while(true){
			System.out.println("\nPlease enter your choice of cipher.");
			int choice = scan.nextInt();

			if(choice == 1){
				System.out.println("\n----------Caesar Cipher----------\n");
				System.out.println("Enter the plain text to cipher.");
				String text = scan.next();

				System.out.println("Enter the key");
				int key = scan.nextInt();

				CaesarCipher cc = new CaesarCipher(text);

				System.out.println("Plain Text  : " + text);
				StringBuffer cipher = cc.encrypt(key);
				System.out.println("Cipher Text : " + cipher);
				System.out.println("Plain Text  : " + cc.decrypt(key, cipher));
			}

			else if(choice == 2) {
				System.out.println("\n----------Mono Alphabetic Cipher----------\n");
				System.out.println("Enter the plain text to cipher.");
				String text = scan.next();
				
				MonoAlphabeticCipher mac = new MonoAlphabeticCipher();
				System.out.println("Plain Text  : " + text);
				String cipher = mac.encrypt(text.toLowerCase());
				System.out.println("Cipher Text : " + cipher);
				System.out.println("Plain Text  : " + mac.decrypt(cipher));	
			}

			else if(choice == 3){
				System.out.println("\n----------Play Fair Cipher----------\n");
				PlayFairCipher pfc = new PlayFairCipher();

				System.out.println("Enter a keyword");
				String keyword = scan.next();
				pfc.setKey(keyword);
				pfc.keyGen();
				System.out.println("Enter the plain text to cipher.");
				String text = scan.next();
				System.out.println("Cipher     : " + pfc.encrypt(text.toLowerCase()));
			}

			else if(choice == 4){
				System.out.println("\n----------Poly Alphabetic Cipher----------\n");
				System.out.println("Enter the plain text to cipher.");
				String text = scan.next();
				System.out.println("Enter the key text.");
				String key = scan.next();

				while(key.length() != text.length()){
					if(key.length() > text.length()){
						key = key.substring(0, text.length());
					}
					else{
						String repeated = new String(new char[2]).replace("\0",key);
						key = repeated;
					}
				}

				System.out.println("Plain Text  : " + text);
				System.out.println("Key Text    : " + key);

				PolyAlphabeticCipher pac = new PolyAlphabeticCipher(text);
				StringBuffer cipher = pac.encrypt(key);

				System.out.println("Cipher Text : " + cipher);
				System.out.println("Plain Text  : " + pac.decrypt(key, cipher));

			}

			else if(choice == 5){
				System.out.println("\n----------One Time Pad Cipher----------\n");
				System.out.println("Enter the plain text to cipher.");
				String text = scan.next();
				System.out.println("Enter the key text.");
				String key = scan.next();

				while(key.length() != text.length()){
					System.out.println("Key length and plain text length is not equal.\nPlease wnter the key again.");
					key = scan.next();					
				}

				System.out.println("Plain Text  : " + text);
				System.out.println("Key Text    : " + key);

				OneTimePadCipher otpc = new OneTimePadCipher(text);
				StringBuffer cipher = otpc.encrypt(key);

				System.out.println("Cipher Text : " + cipher);
				System.out.println("Plain Text  : " + otpc.decrypt(key, cipher));				
			}

			else if(choice == 6){
				System.out.println("Thanks.\n");
				System.exit(0);
			}

			else{
				System.out.println("Wrong choice entered.\n");
			}
		}
	}
}
