package com.phuchaihuynh.binaryconverter;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

public class MainActivity extends Activity implements OnItemSelectedListener {

	public final static String NUMBER = "number";
	public final static String BINARY = "binary";
	public final static String HEX = "hex";
	
	private String pre_input_number = "";
	private String pre_input_binary = "";
	private String pre_input_hex = "";

	EditText number, binary_number, hex_number, sign_bit, exponent, mantissa;
	TextView input_name_1, input_name_2;
	Button convert_button, clear_button;
	Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		input_name_1 = (TextView)findViewById(R.id.input_1_name);
		input_name_2 = (TextView)findViewById(R.id.input_2_name);
		number = (EditText)findViewById(R.id.number); 
		binary_number = (EditText)findViewById(R.id.binary_number);
		hex_number = (EditText)findViewById(R.id.hex_number);
		sign_bit = (EditText)findViewById(R.id.sign_bit);
		exponent = (EditText)findViewById(R.id.exponent_bits);
		mantissa = (EditText)findViewById(R.id.mantissa_bits);

		spinner = (Spinner) findViewById(R.id.converters);
		spinner.setOnItemSelectedListener(this);

		convert_button = (Button)findViewById(R.id.convert_button);

		clear_button = (Button)findViewById(R.id.clear_button);

		View.OnClickListener clear = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				number.setText("");
				binary_number.setText("");
				hex_number.setText("");
				sign_bit.setText("");
				exponent.setText("");
				mantissa.setText("");
				pre_input_number = "";
				pre_input_binary = "";
				pre_input_hex = "";
				hideKeyBoard();
			}
		};
		clear_button.setOnClickListener(clear);
	}

	private void hideKeyBoard() {
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		number.setText("");
		binary_number.setText("");
		hex_number.setText("");
		sign_bit.setText("");
		exponent.setText("");
		mantissa.setText("");
		pre_input_number = "";
		pre_input_binary = "";
		pre_input_hex = "";
		switch(pos) {
		case 0:
			input_name_1.setText("Unsigned Integer Number");
			number.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
			View.OnClickListener oneComplementConverting = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String number_input = number.getText().toString();
					String binary_input = binary_number.getText().toString();
					String hex_input = hex_number.getText().toString();
					
					if (!number_input.equals(pre_input_number)) {
						OneComplementBinary result = new OneComplementBinary(number_input, NUMBER);
						binary_number.setText(result.getOneComplementBinary());
						hex_number.setText(result.getHex());
						pre_input_number = result.getNumber();
						pre_input_binary = result.getOneComplementBinary();
						pre_input_hex = result.getHex();
					}
					else if (!binary_input.equals(pre_input_binary)) {
						OneComplementBinary result = new OneComplementBinary(binary_input, BINARY);
						binary_number.setText(result.getOneComplementBinary());
						hex_number.setText(result.getHex());
						number.setText(result.getNumber());
						pre_input_number = result.getNumber();
						pre_input_binary = result.getOneComplementBinary();
						pre_input_hex = result.getHex();
					}
					else if (!hex_input.equals(pre_input_hex)) {
						OneComplementBinary result = new OneComplementBinary(hex_input, HEX);
						binary_number.setText(result.getOneComplementBinary());
						hex_number.setText(result.getHex());
						number.setText(result.getNumber());
						pre_input_number = result.getNumber();
						pre_input_binary = result.getOneComplementBinary();
						pre_input_hex = result.getHex();
					}
					hideKeyBoard();
				}
			};
			convert_button.setOnClickListener(oneComplementConverting);
			break;
		case 1:
			input_name_1.setText("Signed Integer Number");
			number.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_SIGNED);
			View.OnClickListener twoComplementConverting = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String number_input = number.getText().toString();
					String binary_input = binary_number.getText().toString();
					String hex_input = hex_number.getText().toString();
					if (!number_input.equals(pre_input_number)) {
						TwoComplementBinary result = new TwoComplementBinary(number_input, NUMBER);
						binary_number.setText(result.getTwoComplementBinary());
						hex_number.setText(result.getHex());
						pre_input_number = result.getNumber();
						pre_input_binary = result.getTwoComplementBinary();
						pre_input_hex = result.getHex();
					}
					else if (!binary_input.equals(pre_input_binary)) {
						TwoComplementBinary result = new TwoComplementBinary(binary_input, BINARY);
						binary_number.setText(result.getTwoComplementBinary());
						hex_number.setText(result.getHex());
						number.setText(result.getNumber());
						pre_input_number = result.getNumber();
						pre_input_binary = result.getTwoComplementBinary();
						pre_input_hex = result.getHex();
					}
					else if (!hex_input.equals(pre_input_hex)) {
						TwoComplementBinary result = new TwoComplementBinary(hex_input, HEX);
						binary_number.setText(result.getTwoComplementBinary());
						hex_number.setText(result.getHex());
						number.setText(result.getNumber());
						pre_input_number = result.getNumber();
						pre_input_binary = result.getTwoComplementBinary();
						pre_input_hex = result.getHex();
					}
					hideKeyBoard();
				}
			};
			convert_button.setOnClickListener(twoComplementConverting);
			break;
		case 2:
			input_name_1.setText("Decimal Number");
			number.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
			View.OnClickListener floatConverting = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String number_input = number.getText().toString();
					String binary_input = binary_number.getText().toString();
					String hex_input = hex_number.getText().toString();
					if (!number_input.equals(pre_input_number)) {
						Binary32 result = new Binary32(number_input, NUMBER);
						binary_number.setText(result.getBinary());
						hex_number.setText(result.getHex());
						sign_bit.setText(result.getSignBit());
						exponent.setText(result.getExponentBits());
						mantissa.setText(result.getMantissaBits());
						pre_input_number = result.getNumber();
						pre_input_binary = result.getBinary();
						pre_input_hex = result.getHex();
					}
					else if (!binary_input.equals(pre_input_binary)) {
						Binary32 result = new Binary32(binary_input, BINARY);
						binary_number.setText(result.getBinary());
						hex_number.setText(result.getHex());
						number.setText(result.getNumber());
						sign_bit.setText(result.getSignBit());
						exponent.setText(result.getExponentBits());
						mantissa.setText(result.getMantissaBits());
						pre_input_number = result.getNumber();
						pre_input_binary = result.getBinary();
						pre_input_hex = result.getHex();
					}
					else if (!hex_input.equals(pre_input_hex)) {
						Binary32 result = new Binary32(hex_input, HEX);
						binary_number.setText(result.getBinary());
						hex_number.setText(result.getHex());
						number.setText(result.getNumber());
						sign_bit.setText(result.getSignBit());
						exponent.setText(result.getExponentBits());
						mantissa.setText(result.getMantissaBits());
						pre_input_number = result.getNumber();
						pre_input_binary = result.getBinary();
						pre_input_hex = result.getHex();
					}
					hideKeyBoard();
				}
			};
			convert_button.setOnClickListener(floatConverting);
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub	
	}
}
