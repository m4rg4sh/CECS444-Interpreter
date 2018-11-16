import Symbols.NonTerminal;
import Symbols.Symbol;
import Symbols.Terminal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PredictionTableGenerator {
    private static HashMap<Prediction, Rule> parseTable;

    private PredictionTableGenerator() {
        //hides default constructor
    }

    //TODO Finish prediction table
    public static Map<Prediction, Rule> createPredictionTable(){
        if (parseTable == null){
            fillParseTable();
        }
        
        return parseTable;
    }
    private static void fillRule(ArrayList<Symbol> rhs, Symbol... args){
        Collections.addAll(rhs, args);
    }
    private static void fillMultipleCells(HashMap<Prediction, ArrayList<Symbol>> map,
                                          NonTerminal nonTerminal, ArrayList<Symbol> rules,
                                          Terminal ... terminals){
        for (Terminal terminal : terminals){
            map.put(new Prediction(nonTerminal, terminal), rules);
        }
    }
    
    private static void fillPredictionTable() {
        predictionTable = new HashMap<>();
        
        //PGM = kwdprog Vargroup Fcndefs Main
        ArrayList<Symbol> rhs;
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KPROG, NonTerminal.VARGROUP, NonTerminal.FCNDEFS, NonTerminal.MAIN);
        predictionTable.put(new Prediction(NonTerminal.PGM, Terminal.KPROG), rhs);
        
        //Main = kwdmain BBlock
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KMAIN, NonTerminal.BBLOCK);
        predictionTable.put(new Prediction(NonTerminal.MAIN, Terminal.KMAIN), rhs);
        
        //BBlock = brace1 Vargroup Stmts brace2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACE1, NonTerminal.VARGROUP, NonTerminal.STMTS, Terminal.BRACE2);
        predictionTable.put(new Prediction(NonTerminal.BBLOCK, Terminal.BRACE1), rhs);
        
        //Vargroup = kwdvars PPvarlist
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KVAR, NonTerminal.PPVARLIST);
        predictionTable.put(new Prediction(NonTerminal.VARGROUP, Terminal.KVAR), rhs);
        
        //Vargroup = eps.
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.VARGROUP, Terminal.KVAR), rhs);
    
        //PPvarlist = parens1 Varlist parens2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS1, NonTerminal.VARLIST, Terminal.PARENS2);
        predictionTable.put(new Prediction(NonTerminal.PPVARLIST,Terminal.PARENS1),rhs);
        
        //Varlist = Varitem semi Varlist
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARITEM, Terminal.SEMI, NonTerminal.VARLIST);
        fillMultipleCells(predictionTable, NonTerminal.VARLIST, rhs, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.KCLASS);
        
        //Varlist = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.VARLIST,Terminal.PARENS2),rhs);
    
        //Varitem = Vardecl VaritemT
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARDECL, NonTerminal.VARITEMT);
        fillMultipleCells(predictionTable, NonTerminal.VARITEM, rhs, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.KCLASS);
        
        //Varitem = Classdecl
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASSDECL);
        predictionTable.put(new Prediction(NonTerminal.VARITEM,Terminal.COMMA),rhs);
        
        //VaritemT = equal Varinit
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.EQUAL, NonTerminal.VARINIT);
        predictionTable.put(new Prediction(NonTerminal.VARITEMT,Terminal.EQUAL),rhs);
        
        //VaritemT = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.VARITEMT,Terminal.SEMI),rhs);
    
        //Vardecl = Simplekind Varspec
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.SIMPLEKIND, NonTerminal.VARSPEC);
        fillMultipleCells(predictionTable, NonTerminal.VARDECL, rhs, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID);
        
        //Simplekind = Basekind
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.BASEKIND);
        fillMultipleCells(predictionTable, NonTerminal.SIMPLEKIND, rhs, Terminal.INT, Terminal.FLOAT, Terminal.STRING);
        
        //Simplekind = Classid
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASSID);
        predictionTable.put(new Prediction(NonTerminal.SIMPLEKIND,Terminal.ID),rhs);
    
        //Basekind = int
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.INT);
        predictionTable.put(new Prediction(NonTerminal.BASEKIND,Terminal.INT),rhs);
        
        //Basekind = float
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.FLOAT);
        predictionTable.put(new Prediction(NonTerminal.BASEKIND,Terminal.FLOAT),rhs);
        
        //Basekind = string
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.STRING);
        predictionTable.put(new Prediction(NonTerminal.BASEKIND,Terminal.STRING),rhs);
    
        //Classid = id
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID);
        predictionTable.put(new Prediction(NonTerminal.CLASSID,Terminal.ID),rhs);
    
        //Varspec = Varid VarspecT
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARID, NonTerminal.VARSPECT);
        predictionTable.put(new Prediction(NonTerminal.VARSPEC,Terminal.ID),rhs);
        
        //Varspec = Deref_id
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.DEREF_ID);
        predictionTable.put(new Prediction(NonTerminal.VARSPEC,Terminal.ASTER),rhs);
        
        //VarspecT = KKint
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.KKINT);
        predictionTable.put(new Prediction(NonTerminal.VARSPECT,Terminal.BRACKET1),rhs);
        
        //VarspecT = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(predictionTable, NonTerminal.VARSPECT, rhs, Terminal.EQUAL, Terminal.COMMA);
    
        //Varid = id
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID);
        predictionTable.put(new Prediction(NonTerminal.VARID,Terminal.ID),rhs);
    
        //KKint = bracket1 int bracket2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACKET1, Terminal.INT, Terminal.BRACKET2);
        predictionTable.put(new Prediction(NonTerminal.KKINT,Terminal.BRACKET1),rhs);
    
        //Deref_id = Deref id
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.DEREF, Terminal.ID);
        predictionTable.put(new Prediction(NonTerminal.DEREF_ID,Terminal.ASTER),rhs);
        
        //Deref = aster
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ASTER);
        predictionTable.put(new Prediction(NonTerminal.DEREF,Terminal.ASTER),rhs);
    
        //Varinit = Expr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.EXPR);
        fillMultipleCells(predictionTable, NonTerminal.VARINIT, rhs, Terminal.PARENS1, Terminal.ID,
                Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.ASTER, Terminal.AMPERSAND, Terminal.OPEQ,
                Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS,
                Terminal.SLASH, Terminal.CARET);
        
        //Varinit = BBexprs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.BBEXPRS);
        predictionTable.put(new Prediction(NonTerminal.VARINIT,Terminal.BRACE1),rhs);
    
        //BBexprs = brace1 BBexprsT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACE1, NonTerminal.BBEXPRST);
        predictionTable.put(new Prediction(NonTerminal.BBEXPRS, Terminal.BRACE1), rhs);
        
        //BBexprsT = Exprlist brace2
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.EXPRLIST, Terminal.BRACE2);
        fillMultipleCells(predictionTable, NonTerminal.BBEXPRST, rhs, Terminal.PARENS1, Terminal.ID,
                Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.ASTER, Terminal.AMPERSAND, Terminal.OPEQ,
                Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS,
                Terminal.SLASH, Terminal.CARET);
        
        //BBexprsT = brace2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACE2);
        predictionTable.put(new Prediction(NonTerminal.BBEXPRST,Terminal.BRACE2),rhs);
    
        //Exprlist = Expr Moreexprs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.EXPR, NonTerminal.MOREEXPRS);
        fillMultipleCells(predictionTable, NonTerminal.EXPRLIST, rhs, Terminal.PARENS1, Terminal.ID,
                Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.ASTER, Terminal.AMPERSAND, Terminal.OPEQ,
                Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS,
                Terminal.SLASH, Terminal.CARET);
            
        //Moreexprs = comma Exprlist
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.COMMA, NonTerminal.EXPRLIST);
        predictionTable.put(new Prediction(NonTerminal.MOREEXPRS,Terminal.COMMA),rhs);
        
        //Moreexprs = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(predictionTable, NonTerminal.MOREEXPRS, rhs, Terminal.EQUAL, Terminal.COMMA);
    
        //Classdecl = kwdclass Classid ClassdeclT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KCLASS, NonTerminal.CLASSID, NonTerminal.CLASSDECLT);
        predictionTable.put(new Prediction(NonTerminal.CLASSDECL,Terminal.KCLASS),rhs);
        
        //ClassdeclT = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.CLASSDECLT,Terminal.SEMI),rhs);
        
        //ClassdeclT = Classmom Interfaces BBclassitems
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASSMOM, NonTerminal.INTERFACES, NonTerminal.BBCLASSITEMS);
        predictionTable.put(new Prediction(NonTerminal.CLASSDECLT,Terminal.COMMA),rhs);
    
        //BBClassitems = brace1 Classitems brace2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACE1, NonTerminal.CLASSITEMS, Terminal.BRACE2);
        predictionTable.put(new Prediction(NonTerminal.BBCLASSITEMS,Terminal.BRACE1),rhs);
    
        //Classmom = colon Classid
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.COLON, NonTerminal.CLASSID);
        predictionTable.put(new Prediction(NonTerminal.CLASSMOM,Terminal.COLON),rhs);
        
        //Classmom = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.CLASSMOM,Terminal.BRACE1),rhs);
    
        //Classitems = Classgroup Classitems
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASSGROUP, NonTerminal.CLASSITEMS);
        fillMultipleCells(predictionTable, NonTerminal.CLASSITEMS, rhs, Terminal.INT, Terminal.FLOAT, Terminal.STRING,
                Terminal.ID, Terminal.KCLASS, Terminal.COLON, Terminal.KFCN);
        
        //Classitems = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.CLASSITEMS,Terminal.BRACE2),rhs);
    
        //Classgroup = Class_ctrl
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASS_CTRL);
        predictionTable.put(new Prediction(NonTerminal.CLASSGROUP,Terminal.COLON),rhs);
        
        //Classgroup = Varlist
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARLIST);
        fillMultipleCells(predictionTable, NonTerminal.CLASSGROUP, rhs, Terminal.INT, Terminal.FLOAT, Terminal.STRING,
                Terminal.ID, Terminal.KCLASS);
        
        //Classgroup = Mddecls
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.MDDECLS);
        predictionTable.put(new Prediction(NonTerminal.CLASSGROUP,Terminal.KFCN),rhs);
    
        //Class_ctrl = colon id // in {public, protected, private}
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.COLON, Terminal.ID);
        predictionTable.put(new Prediction(NonTerminal.CLASS_CTRL,Terminal.COLON),rhs);
        
        //Interfaces = colon Classid Interfaces
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.COLON, NonTerminal.CLASSID, NonTerminal.INTERFACES);
        predictionTable.put(new Prediction(NonTerminal.INTERFACES,Terminal.COLON),rhs);
        
        //Interfaces = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.INTERFACES,Terminal.SEMI),rhs);
        
        //Mddecls = Mdheader Mddecls
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.MDHEADER, NonTerminal.MDDECLS);
        predictionTable.put(new Prediction(NonTerminal.MDDECLS,Terminal.KIF),rhs);
        
        //Mddecls = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(predictionTable, NonTerminal.MDDECLS, rhs, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.KCLASS, Terminal.COLON);
        
        //Mdheader = kwdfcn Md_id PParmlist Retkind
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KFCN, NonTerminal.MD_ID, NonTerminal.PPARMLIST, NonTerminal.RETKIND);
        predictionTable.put(new Prediction(NonTerminal.MDHEADER,Terminal.KFCN),rhs);
        
        //Md_id = Classid colon Fcnid
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASSID, Terminal.COLON, NonTerminal.FCNID);
        predictionTable.put(new Prediction(NonTerminal.MD_ID,Terminal.ID),rhs);
        
        //Fcndefs = Fcndef Fcndefs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.FCNDEF, NonTerminal.FCNDEFS);
        predictionTable.put(new Prediction(NonTerminal.FCNDEFS,Terminal.KFCN),rhs);
        
        //Fcndefs = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.FCNDEFS,Terminal.KMAIN),rhs);
        
        //Fcndef = Fcnheader BBlock
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.FCNHEADER, NonTerminal.BBLOCK);
        predictionTable.put(new Prediction(NonTerminal.FCNDEF,Terminal.KFCN),rhs);
        
        //Fcnheader = kwdfcn Fcnid PParmlist Retkind
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KFCN, NonTerminal.FCNID, NonTerminal.PPARMLIST, NonTerminal.RETKIND);
        predictionTable.put(new Prediction(NonTerminal.FCNHEADER,Terminal.KFCN),rhs);
        
        //Fcnid = id
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID);
        predictionTable.put(new Prediction(NonTerminal.FCNID,Terminal.ID),rhs);
        
        //Retkind = Simplekind
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.SIMPLEKIND);
        fillMultipleCells(predictionTable, NonTerminal.RETKIND, rhs, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID);
        
        //PParmlist = parens1 PParmlistT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS1, NonTerminal.PPARMLISTT);
        predictionTable.put(new Prediction(NonTerminal.PPARMLIST,Terminal.PARENS1),rhs);
        
        //PParmlistT = Varspecs parens2
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARSPECS, Terminal.PARENS2);
        fillMultipleCells(predictionTable, NonTerminal.PPARMLISTT, rhs, Terminal.ID, Terminal.ASTER);
        
        //PParmlistT = parens2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS2);
        predictionTable.put(new Prediction(NonTerminal.PPARMLISTT,Terminal.PARENS2),rhs);
        
        //Varspecs = Varspec More_varspecs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARSPEC, NonTerminal.MORE_VARSPECS);
        fillMultipleCells(predictionTable, NonTerminal.VARSPECS, rhs, Terminal.ID, Terminal.ASTER);
        
        //More_varspecs = comma Varspecs
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.COMMA, NonTerminal.VARSPECS);
        predictionTable.put(new Prediction(NonTerminal.MORE_VARSPECS,Terminal.COMMA),rhs);
        
        //More_varspecs = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.MORE_VARSPECS,Terminal.PARENS2),rhs);
        
        //PPonly = parens1 parens2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS1, Terminal.PARENS2);
        predictionTable.put(new Prediction(NonTerminal.PPONLY,Terminal.PARENS1),rhs);
        
        //Stmts = Stmt semi Stmts
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.STMT, Terminal.SEMI, NonTerminal.STMTS);
        fillMultipleCells(predictionTable, NonTerminal.STMTS, rhs, Terminal.ID, Terminal.ASTER, Terminal.KIF,
                Terminal.KWHILE, Terminal.KPRINT, Terminal.KRETURN);
        
        //Stmts = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.STMTS,Terminal.BRACE2),rhs);
        
        //Stmt = Stif
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.STIF);
        predictionTable.put(new Prediction(NonTerminal.STMT,Terminal.KIF),rhs);
        
        //Stmt = Stwhile
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.STWHILE);
        predictionTable.put(new Prediction(NonTerminal.STMT,Terminal.KWHILE),rhs);
        
        //Stmt = Stprint
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.STPRINT);
        predictionTable.put(new Prediction(NonTerminal.STMT,Terminal.KPRINT),rhs);
        
        //Stmt = Strtn
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.STRTN);
        predictionTable.put(new Prediction(NonTerminal.STMT,Terminal.KRETURN),rhs);
        
        //Stmt = Deref_id equal Expr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.DEREF_ID, Terminal.EQUAL, NonTerminal.EXPR);
        predictionTable.put(new Prediction(NonTerminal.STMT,Terminal.ASTER),rhs);
        
        //Stmt = id StmtT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID, NonTerminal.STMTT);
        predictionTable.put(new Prediction(NonTerminal.STMT,Terminal.ID),rhs);
        
        //StmtT = LvalT
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.LVALT);
        predictionTable.put(new Prediction(NonTerminal.STMTT,Terminal.BRACE1),rhs);
        
        //StmtT = PPexprs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.PPEXPRS);
        predictionTable.put(new Prediction(NonTerminal.STMTT,Terminal.PARENS1),rhs);
        
//        //Stasgn = Lval equal Expr
//        rhs = new ArrayList<>();
//        fillRule(rhs, NonTerminal.LVAL, Terminal.EQUAL, NonTerminal.EXPR);
//        fillMultipleCells(predictionTable, NonTerminal.STASGN, rhs, Terminal.ID, Terminal.ASTER);
        
        //Lval = id LvalT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID, NonTerminal.LVALT);
        predictionTable.put(new Prediction(NonTerminal.LVAL,Terminal.ID),rhs);
        
        //Lval = Deref_id
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.DEREF_ID);
        predictionTable.put(new Prediction(NonTerminal.LVAL,Terminal.ASTER),rhs);
        
        //LvalT = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(predictionTable, NonTerminal.LVALT, rhs, Terminal.EQUAL, Terminal.SEMI);
        
        //LvalT = KKexpr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.KKEXPR);
        predictionTable.put(new Prediction(NonTerminal.LVALT,Terminal.BRACKET1),rhs);
        
        //KKexpr = bracket1 Expr bracket2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACKET1, NonTerminal.EXPR, Terminal.BRACKET2);
        predictionTable.put(new Prediction(NonTerminal.KKEXPR,Terminal.BRACKET1),rhs);
        
        //Fcall = Fcnid PPexprs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.FCNID, NonTerminal.PPEXPRS);
        predictionTable.put(new Prediction(NonTerminal.FCALL,Terminal.ID),rhs);
        
        //PPexprs = parens1
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS1);
        predictionTable.put(new Prediction(NonTerminal.PPEXPRS,Terminal.PARENS1),rhs);
        
        //PPexprsT = Exprlist parens2
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.EXPRLIST, Terminal.PARENS2);
        fillMultipleCells(predictionTable, NonTerminal.PPEXPRST, rhs, Terminal.PARENS1, Terminal.ID,
                Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.ASTER, Terminal.AMPERSAND, Terminal.OPEQ,
                Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS,
                Terminal.SLASH, Terminal.CARET);
        
        //PPexprsT = parens2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS2);
        predictionTable.put(new Prediction(NonTerminal.PPEXPRST,Terminal.PARENS2),rhs);
    
        //Stif = kwdif PPexpr BBlock Elsepart
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KIF, NonTerminal.PPEXPR, NonTerminal.BBLOCK, NonTerminal.ELSEPART);
        predictionTable.put(new Prediction(NonTerminal.STIF,Terminal.KIF),rhs);
        
        //Elsepart = kwdelseif PPexpr BBlock Elsepart
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KELSEIF, NonTerminal.PPEXPR, NonTerminal.BBLOCK, NonTerminal.ELSEPART);
        predictionTable.put(new Prediction(NonTerminal.ELSEPART,Terminal.KELSEIF),rhs);
        
        //Elsepart = kwdelse BBlock
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KELSE, NonTerminal.BBLOCK);
        predictionTable.put(new Prediction(NonTerminal.ELSEPART,Terminal.KELSE),rhs);
        
        //Elsepart = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.ELSEPART,Terminal.SEMI),rhs);
        
        //Stwhile = kwdwhile PPexpr BBlock
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KWHILE, NonTerminal.PPEXPR, NonTerminal.BBLOCK);
        predictionTable.put(new Prediction(NonTerminal.STWHILE,Terminal.KWHILE),rhs);
        
        //Stprint = kprint PPexprs
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KPRINT, NonTerminal.PPEXPRS);
        predictionTable.put(new Prediction(NonTerminal.STPRINT,Terminal.KPRINT),rhs);
        
        //Strtn = kwdreturn StrtnT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KRETURN, NonTerminal.STRTNT);
        predictionTable.put(new Prediction(NonTerminal.STRTN,Terminal.KRETURN),rhs);
        
        //StrtnT = Expr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.EXPR);
        fillMultipleCells(predictionTable, NonTerminal.STRTNT, rhs, Terminal.PARENS1, Terminal.ID,
                Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.ASTER, Terminal.AMPERSAND, Terminal.OPEQ,
                Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS,
                Terminal.SLASH, Terminal.CARET);
        
        //StrtnT = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(predictionTable, NonTerminal.STRTNT, rhs, Terminal.SEMI);
        
        //PPexpr = parens1 Expr parens2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS1, NonTerminal.EXPR, Terminal.PARENS2);
        predictionTable.put(new Prediction(NonTerminal.PPEXPR,Terminal.PARENS1),rhs);
        
        //Expr = Oprel Rterm Expr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.OPREL, NonTerminal.RTERM, NonTerminal.EXPR);
        fillMultipleCells(predictionTable, NonTerminal.EXPR, rhs, Terminal.AMPERSAND, Terminal.OPEQ, Terminal.OPLE);
        
        //Expr = Rterm Expr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.RTERM, NonTerminal.EXPR);
        fillMultipleCells(predictionTable, NonTerminal.EXPR, rhs, Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1,
                Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS, Terminal.SLASH, Terminal.CARET);
        
        //Expr = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(predictionTable, NonTerminal.EXPR, rhs, Terminal.PARENS2, Terminal.SEMI);
        
        //Rterm = Opadd Term Rterm
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.OPADD, NonTerminal.TERM, NonTerminal.RTERM);
        fillMultipleCells(predictionTable, NonTerminal.RTERM, rhs, Terminal.PLUS, Terminal.MINUS);
        
        //Rterm = Term Rterm
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.TERM, NonTerminal.RTERM);
        fillMultipleCells(predictionTable, NonTerminal.RTERM, rhs, Terminal.PARENS1, Terminal.INT, Terminal.ASTER,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.AMPERSAND, Terminal.SLASH, Terminal.CARET);
        
        //Rterm = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.RTERM,Terminal.),rhs);
        
        //Term = Opmul Factcheck Term
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.OPMUL, NonTerminal.FACTCHECK, NonTerminal.TERM);
        fillMultipleCells(predictionTable, NonTerminal.TERM, rhs, Terminal.ASTER, Terminal.SLASH, Terminal.CARET);
        
        //Term = Fact Term
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.FACT, NonTerminal.TERM);
        fillMultipleCells(predictionTable, NonTerminal.TERM, rhs, Terminal.PARENS1, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.AMPERSAND);
        
        //Term = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.Term,Terminal.),rhs);
        
        //Factcheck = Fact
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.FACT);
        fillMultipleCells(predictionTable, NonTerminal.FACTCHECK, rhs, Terminal.PARENS1, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.AMPERSAND);
        
        //Factcheck = Deref_id
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.DEREF_ID);
        predictionTable.put(new Prediction(NonTerminal.FACTCHECK,Terminal.ASTER),rhs);
        
        //Fact = Basekind
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.BASEKIND);
        fillMultipleCells(predictionTable, NonTerminal.BASEKIND, rhs, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING);
        
        //Fact = Addrof_id
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.ADDROF_ID);
        predictionTable.put(new Prediction(NonTerminal.FACT,Terminal.AMPERSAND),rhs);
        
        //Fact = PPexpr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.PPEXPR);
        predictionTable.put(new Prediction(NonTerminal.FACT,Terminal.PARENS1),rhs);
        
        //Fact = id FactT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID, NonTerminal.FACTT);
        predictionTable.put(new Prediction(NonTerminal.FACT,Terminal.ID),rhs);
        
        //FactT = PPexprs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.PPEXPRS);
        predictionTable.put(new Prediction(NonTerminal.FACTT,Terminal.PARENS1),rhs);
        
        //FactT = KKexpr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.KKEXPR);
        predictionTable.put(new Prediction(NonTerminal.FACTT,Terminal.BRACKET1),rhs);
        
        //FactT = eps
        rhs = new ArrayList<>();
        
        predictionTable.put(new Prediction(NonTerminal.FACTT,Terminal.),rhs);
        
        //Addrof_id = ampersand id
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.AMPERSAND, Terminal.ID);
        predictionTable.put(new Prediction(NonTerminal.ADDROF_ID,Terminal.AMPERSAND),rhs);
        
        //Oprel = opeq
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.OPEQ);
        predictionTable.put(new Prediction(NonTerminal.OPREL,Terminal.OPEQ),rhs);
        
        //Oprel = opne
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.OPNE);
        predictionTable.put(new Prediction(NonTerminal.OPREL,Terminal.OPNE),rhs);
        
        //Oprel = Lthan
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.LTHAN);
        predictionTable.put(new Prediction(NonTerminal.OPREL,Terminal.ANGLE1),rhs);
        
        //Oprel = ople
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.OPLE);
        predictionTable.put(new Prediction(NonTerminal.OPREL,Terminal.OPLE),rhs);
        
        //Oprel = opge
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.OPGE);
        predictionTable.put(new Prediction(NonTerminal.OPREL,Terminal.OPGE),rhs);
        
        //Oprel = Gthan
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.GTHAN);
        predictionTable.put(new Prediction(NonTerminal.OPREL,Terminal.ANGLE2),rhs);
        
        //Lthan = angle1
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ANGLE1);
        predictionTable.put(new Prediction(NonTerminal.LTHAN,Terminal.ANGLE1),rhs);
        
        //Gthan = angle2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ANGLE2);
        predictionTable.put(new Prediction(NonTerminal.GTHAN,Terminal.ANGLE2),rhs);
        
        //Opadd = plus
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PLUS);
        predictionTable.put(new Prediction(NonTerminal.OPADD,Terminal.PLUS),rhs);
        
        //Opadd = minus
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.MINUS);
        predictionTable.put(new Prediction(NonTerminal.OPADD,Terminal.MINUS),rhs);
        
        //Opmul = aster
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ASTER);
        predictionTable.put(new Prediction(NonTerminal.OPMUL,Terminal.ASTER),rhs);
        
        //Opmul = slash
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.SLASH);
        predictionTable.put(new Prediction(NonTerminal.OPMUL,Terminal.SLASH),rhs);
        
        //Opmul = caret
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.CARET);
        predictionTable.put(new Prediction(NonTerminal.OPMUL,Terminal.CARET),rhs);
        
    }
}
