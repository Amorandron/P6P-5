package com.janwilts.bigmovie.parser.parsers;

import com.janwilts.bigmovie.parser.util.RomanNumeral;

import java.io.File;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Everdien
 */
public class SoundtrackParser extends Parser {
    public SoundtrackParser(File file) {
        super(file);
    }
    
    @Override
    public void parse() {
        try (PrintWriter writer = new PrintWriter(this.csv, "UTF-8")) {
            String titleMoviePattern = "#\\s(.*?)\\s\\((.{4})(|\\/(.*?))\\)(\\s\\((.*?)\\)|)\n";
            Pattern mp = Pattern.compile(titleMoviePattern);

            /*
            regex movietitleline: #\s(.*?)\s\((.{4})(|\/(.*?))\)(\s\((.*?)\)|)\n
            # --> matches the character # literally --> first character of line with movie title
            \s --> matches any whitespace character --> space before title
            (.*?) --> (first group) matches any character --> movie title
            \s --> matches any whitespace character --> space after title
            \( --> matches the '(' character; first character before the year of the movie
            (.{4}) --> (second group) matches any character exactly 4 times; --> year
            (|\/(.*?)) --> (third group) matches null or '/' character and (fourth group) matches any character --> romanNumber
            \) --> matches the ')' character; first character after the year of the movie
            (\s\((.*?)\)|) --> (fifth group) matches null or '(' and (sixth group) matches any character till ')' --> type (TV/V/VG)
            \n --> matches enter

            \1 --> movie title
            \2 --> year of movie
            \4 --> romans number (only movies with same title as year, but different movies are)
            \6 --> type (TV/V/VG)
             */
            
            String titleSongPattern = "-\\s\"(.*?)\"";
            Pattern sp = Pattern.compile(titleSongPattern);

            /*
            regex sontitleline: \-\s\"(.*?)\"
            \- --> matches the character - literally --> first character of the line with song title
            \s --> matches any whitespace character --> space before song title
            \" --> matches the character " literally --> character before song title
            (.*?) --> matches any character --> song title
            \" --> matches the character " literally --> character after song title

            \1 --> song title
             */
            
            Boolean foundList = false;
            String line;
            String currentMovieTitle = "";
            String currentMovieYear = "";
            String currentType = "";
            String currentRomanNumber = "";
            String currentSong = "";
            
            while (((line = this.readLine()) != null)) {
                if (!foundList && line.equals("SOUNDTRACKS")) foundList = true;
                if (foundList) {
                    Matcher mm = mp.matcher(line);
                    
                    if (mm.matches()) {
                        //go to next line if it's a serie
                        if (mm.group(1).startsWith(QUOTE)) continue;
                        
                        currentMovieTitle = mm.group(1);
                        currentType = mm.group(6);
                        
                        if (mm.group(2).contains("?")) currentMovieYear = "";
                        else currentMovieYear = mm.group(2);
                        
                        if (mm.group(4) == null) currentRomanNumber = "0";
                        else currentRomanNumber = Integer.toString(RomanNumeral.convert(mm.group(4)));
                        
                        //got to next line
                        line = reader.readLine().trim();
                        
                        //while line is not empty check for song matches
                        do {
                            Matcher sm = sp.matcher(line);
                            
                            if (sm.matches()) {
                                currentSong = sm.group(1);
                                
                                writer.println(String.join(DELIMITER, addQuotes(currentMovieTitle), currentMovieYear, addQuotes(currentType), currentRomanNumber, addQuotes(currentSong)));
                            }
                            
                            line = reader.readLine().trim();
                        }
                        while (!line.isEmpty());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
