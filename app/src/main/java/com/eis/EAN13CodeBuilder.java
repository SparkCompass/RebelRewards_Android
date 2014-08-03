


package com.eis;



public class EAN13CodeBuilder {
       private String codeStringValue;
    private String generatedCode;

    public EAN13CodeBuilder(String codeString)
    {
        codeStringValue = codeString;
        parse();
    }

     public String getCode()
    {
        return generatedCode;

    }

    //////////////////////////////////////////////////////////

    private String getFullCode()
    {
      
       int chetVal = 0, nechetVal = 0;
       String codeToParse = codeStringValue;

       for( int index = 0;index<6;index++ )
       {
          chetVal += Integer.valueOf(codeToParse.substring(index*2+1,index*2+2)).intValue();
          nechetVal += Integer.valueOf(codeToParse.substring(index*2,index*2+1)).intValue();
       }

       chetVal *= 3;
       int controlNumber = 10 - (chetVal+nechetVal)%10;
       if( controlNumber == 10 ) controlNumber  = 0;

       codeToParse += String.valueOf(controlNumber);

       return codeToParse;

    }

    private String DigitToUpperCase( String digit)
    {
       String letters  = "ABCDEFGHIJ";
       int position = Integer.valueOf(digit).intValue();

       String retVal = letters.substring(position,position+1);

       return retVal;

    }

    private String DigitToLowerCase( String digit)
    {
       String letters  = "abcdefghij";
       int position = Integer.valueOf(digit).intValue();

       String retVal = letters.substring(position,position+1);

       return retVal;

    }
    
   /* public String createEAN13Code(String DataToEncode)
	{
    String DataToPrint = "";
    String OnlyCorrectData = "";
    int CurrentCharNum = 0;
    char CurrentEncoding;
    //Check to make sure data is numeric and remove dashes, etc.
    int StringLength = DataToEncode.length();
    for (int I = 0; I < StringLength; I++)
    {
         //Add all numbers to OnlyCorrectData string
         CurrentCharNum = (int)(DataToEncode.charAt(I));
         if (CurrentCharNum > 47 && CurrentCharNum < 58)
         	OnlyCorrectData = OnlyCorrectData + DataToEncode.charAt(I);
    }
    //2006.2 BDA added the next line for general compatibility
    StringLength = OnlyCorrectData.length();
    if (StringLength < 12)
    	OnlyCorrectData = "0000000000000";
    if (StringLength == 16)
    	OnlyCorrectData = "0000000000000";
    if (StringLength == 13)
    	OnlyCorrectData = OnlyCorrectData.substring(0, 12);
    if (StringLength == 15)
    	OnlyCorrectData = OnlyCorrectData.substring(0, 12) + OnlyCorrectData.substring(13, 15);
    if (StringLength > 17)
    	OnlyCorrectData = OnlyCorrectData.substring(0, 12) + OnlyCorrectData.substring(13, 17);
  
    //2006.2 BDA added the next line for general compatibility
    StringLength = OnlyCorrectData.length();
   
    //Remove digit number from add-ons and check digit
    DataToEncode = OnlyCorrectData.substring(0, 12);
    //Calculate Check Digit
    int Factor = 3;
    int WeightedTotal = 0;
    for (int I = DataToEncode.length() - 1; I >= 0; I--)
    {
        //Get the value of each number starting at the end
        CurrentCharNum = Integer.parseInt("" + DataToEncode.charAt(I));
        //Multiply by the weighting factor which is 3,1,3,1...
        //and add the sum together
        WeightedTotal = WeightedTotal + CurrentCharNum * Factor;
        //Change factor for next calculation
        Factor = 4 - Factor;
    }
    //Find the CheckDigit by finding the number + WeightedTotal that = a multiple of 10
    //Divide by 10, get the remainder and subtract from 10
    int I = WeightedTotal % 10;
    int CheckDigit = 0;
    if (I != 0)
        CheckDigit = 10 - I;
    else
        CheckDigit = 0;
    //Encode the leading digit into the left half of the EAN-13 symbol
    //by using variable parity between character sets A and B
    int LeadingDigit = Integer.parseInt("" + DataToEncode.charAt(0));
    String Encoding = "";
    switch (LeadingDigit)
    {
	    case 0:
	    	Encoding = "AAAAAACCCCCC";
	    	break;
	    case 1:
	    	Encoding = "AABABBCCCCCC";
	    	break;
	    case 2:
	    	Encoding = "AABBABCCCCCC";
	    	break;
	    case 3:
	    	Encoding = "AABBBACCCCCC";
	    	break;
	    case 4:
	    	Encoding = "ABAABBCCCCCC";
	    	break;
	    case 5:
	    	Encoding = "ABBAABCCCCCC";
	    	break;
	    case 6:
	    	Encoding = "ABBBAACCCCCC";
	    	break;
	    case 7:
	    	Encoding = "ABABABCCCCCC";
	    	break;
	    case 8:
	    	Encoding = "ABABBACCCCCC";
	    	break;
	    case 9:
	    	Encoding = "ABBABACCCCCC";
	    	break;
	    default:
		    break;
  	}
    //Add the check digit to the end of the barcode & remove the leading digit
    DataToEncode = DataToEncode.substring(1, 12) + CheckDigit;
    //Determine character to print for proper barcoding
    StringLength = DataToEncode.length();
    for (I = 0; I < StringLength; I++)
    {
    	//Get the ASCII value of each number excluding the first number because
    	//it is encoded with variable parity
    	CurrentCharNum = (int)DataToEncode.charAt(I);
	    CurrentEncoding = Encoding.charAt(I);
	    //Print different barcodes according to the location of the CurrentChar and CurrentEncoding
	    switch (CurrentEncoding)
	    {
		    case 'A':
		         DataToPrint = DataToPrint + (char)(CurrentCharNum);
		         break;
		    case 'B':
		         DataToPrint = DataToPrint + (char)(CurrentCharNum + 17);
		         break;
		    case 'C':
		         DataToPrint = DataToPrint + (char)(CurrentCharNum + 27);
		         break;
		  }
		    //Add in the 1st character along with guard patterns
		  switch (I)
		  {
		    case 0:
		        //For the LeadingDigit, print the human readable character,
		        //the normal guard pattern, and then the rest of the barcode
		        if (LeadingDigit > 4)
		        	DataToPrint = (char)((LeadingDigit + 48) + 64) + "(" + DataToPrint;
		        if (LeadingDigit < 5)
		        	DataToPrint = (char)((LeadingDigit + 48) + 37) + "(" + DataToPrint;
		        	break;
		    case 5:
		        //Print the center guard pattern after the 6th character
		        DataToPrint = DataToPrint + "*";
		        break;
		    case 11:
		        //For the last character (12), print the the normal guard pattern after the barcode
		        DataToPrint = DataToPrint + "(";
		        break;
	    }
  	}
    //Process add-ons if they exist
    
    //Return PrintableString
    return DataToPrint;
	}*/

    private String createEAN13Code(String rawCode)
    {
         int firstFlag = Integer.valueOf(

                rawCode.substring(0,1)

        ).intValue();

         String leftString = rawCode.substring(1,7);
         String rightString = rawCode.substring(7);

         String rightCode = "";
         String leftCode = "";

         for( int i=0;i<6;i++)
         {
           rightCode += DigitToLowerCase( rightString.substring(i,i+1) );
         }



         if( firstFlag == 0 )
         {
            leftCode = "#!"+leftString.substring(0,1)
                           +leftString.substring(1,2)
                           +leftString.substring(2,3)
                           +leftString.substring(3,4)
                           +leftString.substring(4,5)
                           +leftString.substring(5);
         }
         if( firstFlag == 1 )
         {
            ///System.out.println("leftString: "+leftString);
            leftCode = "$!"+leftString.substring(0,1)
                           +leftString.substring(1,2)
                           +DigitToUpperCase(leftString.substring(2,3))
                           +leftString.substring(3,4)
                           +DigitToUpperCase(leftString.substring(4,5))
                           +DigitToUpperCase(leftString.substring(5));
         }
         if( firstFlag == 2 )
         {
            leftCode = "%!"+leftString.substring(0,1)
                           +leftString.substring(1,2)
                           +DigitToUpperCase(leftString.substring(2,3))
                           +DigitToUpperCase(leftString.substring(3,4))
                           +leftString.substring(4,5)
                           +DigitToUpperCase(leftString.substring(5));
         }
         if( firstFlag == 3 )
         {
            leftCode = "&!"+leftString.substring(0,1)
                         +leftString.substring(1,2)
                         +DigitToUpperCase(leftString.substring(2,3))
                         +DigitToUpperCase(leftString.substring(3,4))
                         +DigitToUpperCase(leftString.substring(4,5))
                         +leftString.substring(5);
         }
         if( firstFlag == 4 )
         {
            leftCode = "'!"+leftString.substring(0,1)
                         +DigitToUpperCase(leftString.substring(1,2))
                         +leftString.substring(2,3)
                         +leftString.substring(3,4)
                         +DigitToUpperCase(leftString.substring(4,5))
                         +DigitToUpperCase(leftString.substring(5));
         }
         if( firstFlag == 5 )
         {
            leftCode = "(!"+leftString.substring(0,1)
                         +DigitToUpperCase(leftString.substring(1,2))
                         +DigitToUpperCase(leftString.substring(2,3))
                         +leftString.substring(3,4)
                         +leftString.substring(4,5)
                         +DigitToUpperCase(leftString.substring(5));
         }
         if( firstFlag == 6 )
         {
            leftCode = ")!"+leftString.substring(0,1)
                           +DigitToUpperCase(leftString.substring(1,2))
                           +DigitToUpperCase(leftString.substring(2,3))
                           +DigitToUpperCase(leftString.substring(3,4))
                           +leftString.substring(4,5)
                           +leftString.substring(5);
         }
         if( firstFlag == 7 )
         {
            leftCode = "*!"+leftString.substring(0,1)
                         +DigitToUpperCase(leftString.substring(1,2))
                         +leftString.substring(2,3)
                         +DigitToUpperCase(leftString.substring(3,4))
                         +leftString.substring(4,5)
                         +DigitToUpperCase(leftString.substring(5));
         }
         if( firstFlag == 8 )
         {
            leftCode = "+!"+leftString.substring(0,1)
                         +DigitToUpperCase(leftString.substring(1,2))
                         +leftString.substring(2,3)
                         +DigitToUpperCase(leftString.substring(3,4))
                         +DigitToUpperCase(leftString.substring(4,5))
                         +leftString.substring(5);
         }
         if( firstFlag == 9 )
         {
            leftCode = ",!"+leftString.substring(0,1)
                         +DigitToUpperCase(leftString.substring(1,2))
                         +DigitToUpperCase(leftString.substring(2,3))
                         +leftString.substring(3,4)
                         +DigitToUpperCase(leftString.substring(4,5))
                         +leftString.substring(5);
         }



         String retVal = leftCode + "-" + rightCode + "!";

         return retVal;
    }

    private void parse()
    {
       String fullString = getFullCode();
       System.out.println( "Full code: " + fullString );

       generatedCode = createEAN13Code(fullString);

       System.out.println( "Generated code: " + generatedCode );

    }


}
