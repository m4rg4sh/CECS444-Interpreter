import Symbols.NonTerminal;
import Symbols.Symbol;
import Symbols.Terminal;

import java.util.ArrayList;
import java.util.Collections;

public class PredictionTableGenerator {
    private static ParseTable parseTable;

    private PredictionTableGenerator() {
        //hides default constructor
    }

    public static ParseTable createParseTable(){
        if (parseTable == null){
            fillParseTable();
        }
        
        return parseTable;
    }
    private static void fillRule(ArrayList<Symbol> rhs, Symbol... args){
        Collections.addAll(rhs, args);
    }
    private static void fillMultipleCells(ParseTable map,
                                          NonTerminal nonTerminal, ArrayList<Symbol> rules, int ruleNumber,
                                          Terminal ... terminals){
        for (Terminal terminal : terminals){
            map.put(new Prediction(nonTerminal, terminal), rules, ruleNumber);
        }
    }
    
    private static void fillParseTable() {
        parseTable = new ParseTable();
        int ruleNumber = 1;
        
        //PGM = kwdprog Vargroup Fcndefs Main
        ArrayList<Symbol> rhs;
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KPROG, NonTerminal.VARGROUP, NonTerminal.FCNDEFS, NonTerminal.MAIN);
        parseTable.put(new Prediction(NonTerminal.PGM, Terminal.KPROG), rhs, ruleNumber++);
        
        //Main = kwdmain BBlock
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KMAIN, NonTerminal.BBLOCK);
        parseTable.put(new Prediction(NonTerminal.MAIN, Terminal.KMAIN), rhs, ruleNumber++);
        
        //BBlock = brace1 Vargroup Stmts brace2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACE1, NonTerminal.VARGROUP, NonTerminal.STMTS, Terminal.BRACE2);
        parseTable.put(new Prediction(NonTerminal.BBLOCK, Terminal.BRACE1), rhs, ruleNumber++);
        
        //Vargroup = kwdvars PPvarlist
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KVAR, NonTerminal.PPVARLIST);
        parseTable.put(new Prediction(NonTerminal.VARGROUP, Terminal.KVAR), rhs, ruleNumber++);
        
        //Vargroup = eps.
        rhs = new ArrayList<>();
        
        fillMultipleCells(parseTable, NonTerminal.VARGROUP, rhs, ruleNumber++, Terminal.ID, Terminal.ASTER, Terminal.KMAIN,
                Terminal.KFCN, Terminal.KIF, Terminal.KWHILE, Terminal.KPRINT, Terminal.KRETURN, Terminal.BRACE2);
    
        //PPvarlist = parens1 Varlist parens2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS1, NonTerminal.VARLIST, Terminal.PARENS2);
        parseTable.put(new Prediction(NonTerminal.PPVARLIST,Terminal.PARENS1), rhs, ruleNumber++);
        
        //Varlist = Varitem semi Varlist
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARITEM, Terminal.SEMI, NonTerminal.VARLIST);
        fillMultipleCells(parseTable, NonTerminal.VARLIST, rhs, ruleNumber++, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.KCLASS);
        
        //Varlist = eps
        rhs = new ArrayList<>();
        
        parseTable.put(new Prediction(NonTerminal.VARLIST,Terminal.PARENS2), rhs, ruleNumber++);
    
        //Varitem = Vardecl VaritemT
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARDECL, NonTerminal.VARITEMT);
        fillMultipleCells(parseTable, NonTerminal.VARITEM, rhs, ruleNumber++, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.KCLASS);
        
        //Varitem = Classdecl
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASSDECL);
        parseTable.put(new Prediction(NonTerminal.VARITEM,Terminal.COMMA), rhs, ruleNumber++);
        
        //VaritemT = equal Varinit
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.EQUAL, NonTerminal.VARINIT);
        parseTable.put(new Prediction(NonTerminal.VARITEMT,Terminal.EQUAL), rhs, ruleNumber++);
        
        //VaritemT = eps
        rhs = new ArrayList<>();
        
        parseTable.put(new Prediction(NonTerminal.VARITEMT,Terminal.SEMI), rhs, ruleNumber++);
    
        //Vardecl = Simplekind Varspec
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.SIMPLEKIND, NonTerminal.VARSPEC);
        fillMultipleCells(parseTable, NonTerminal.VARDECL, rhs, ruleNumber++, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID);
        
        //Simplekind = Basekind
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.BASEKIND);
        fillMultipleCells(parseTable, NonTerminal.SIMPLEKIND, rhs, ruleNumber++, Terminal.INT, Terminal.FLOAT, Terminal.STRING);
        
        //Simplekind = Classid
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASSID);
        parseTable.put(new Prediction(NonTerminal.SIMPLEKIND,Terminal.ID), rhs, ruleNumber++);
    
        //Basekind = int
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.INT);
        parseTable.put(new Prediction(NonTerminal.BASEKIND,Terminal.INT), rhs, ruleNumber++);
        
        //Basekind = float
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.FLOAT);
        parseTable.put(new Prediction(NonTerminal.BASEKIND,Terminal.FLOAT), rhs, ruleNumber++);
        
        //Basekind = string
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.STRING);
        parseTable.put(new Prediction(NonTerminal.BASEKIND,Terminal.STRING), rhs, ruleNumber++);
    
        //Classid = id
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID);
        parseTable.put(new Prediction(NonTerminal.CLASSID,Terminal.ID), rhs, ruleNumber++);
    
        //Varspec = Varid VarspecT
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARID, NonTerminal.VARSPECT);
        parseTable.put(new Prediction(NonTerminal.VARSPEC,Terminal.ID), rhs, ruleNumber++);
        
        //Varspec = Deref_id
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.DEREF_ID);
        parseTable.put(new Prediction(NonTerminal.VARSPEC,Terminal.ASTER), rhs, ruleNumber++);
        
        //VarspecT = KKint
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.KKINT);
        parseTable.put(new Prediction(NonTerminal.VARSPECT,Terminal.BRACKET1), rhs, ruleNumber++);
        
        //VarspecT = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(parseTable, NonTerminal.VARSPECT, rhs, ruleNumber++, Terminal.EQUAL, Terminal.COMMA);
    
        //Varid = id
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID);
        parseTable.put(new Prediction(NonTerminal.VARID,Terminal.ID), rhs, ruleNumber++);
    
        //KKint = bracket1 int bracket2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACKET1, Terminal.INT, Terminal.BRACKET2);
        parseTable.put(new Prediction(NonTerminal.KKINT,Terminal.BRACKET1), rhs, ruleNumber++);
    
        //Deref_id = Deref id
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.DEREF, Terminal.ID);
        parseTable.put(new Prediction(NonTerminal.DEREF_ID,Terminal.ASTER), rhs, ruleNumber++);
        
        //Deref = aster
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ASTER);
        parseTable.put(new Prediction(NonTerminal.DEREF,Terminal.ASTER), rhs, ruleNumber++);
    
        //Varinit = Expr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.EXPR);
        fillMultipleCells(parseTable, NonTerminal.VARINIT, rhs, ruleNumber++, Terminal.PARENS1, Terminal.ID,
                Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.ASTER, Terminal.AMPERSAND, Terminal.OPEQ,
                Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS,
                Terminal.SLASH, Terminal.CARET);
        
        //Varinit = BBexprs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.BBEXPRS);
        parseTable.put(new Prediction(NonTerminal.VARINIT,Terminal.BRACE1), rhs, ruleNumber++);
    
        //BBexprs = brace1 BBexprsT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACE1, NonTerminal.BBEXPRST);
        parseTable.put(new Prediction(NonTerminal.BBEXPRS, Terminal.BRACE1), rhs, ruleNumber++);
        
        //BBexprsT = Exprlist brace2
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.EXPRLIST, Terminal.BRACE2);
        fillMultipleCells(parseTable, NonTerminal.BBEXPRST, rhs, ruleNumber++, Terminal.PARENS1, Terminal.ID,
                Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.ASTER, Terminal.AMPERSAND, Terminal.OPEQ,
                Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS,
                Terminal.SLASH, Terminal.CARET);
        
        //BBexprsT = brace2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACE2);
        parseTable.put(new Prediction(NonTerminal.BBEXPRST,Terminal.BRACE2), rhs, ruleNumber++);
    
        //Exprlist = Expr Moreexprs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.EXPR, NonTerminal.MOREEXPRS);
        fillMultipleCells(parseTable, NonTerminal.EXPRLIST, rhs, ruleNumber++, Terminal.PARENS1, Terminal.ID,
                Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.ASTER, Terminal.AMPERSAND, Terminal.OPEQ,
                Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS,
                Terminal.SLASH, Terminal.CARET);
            
        //Moreexprs = comma Exprlist
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.COMMA, NonTerminal.EXPRLIST);
        parseTable.put(new Prediction(NonTerminal.MOREEXPRS,Terminal.COMMA), rhs, ruleNumber++);
        
        //Moreexprs = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(parseTable, NonTerminal.MOREEXPRS, rhs, ruleNumber++, Terminal.BRACE2, Terminal.PARENS2);
    
        //Classdecl = kwdclass Classid ClassdeclT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KCLASS, NonTerminal.CLASSID, NonTerminal.CLASSDECLT);
        parseTable.put(new Prediction(NonTerminal.CLASSDECL,Terminal.KCLASS), rhs, ruleNumber++);
        
        //ClassdeclT = eps
        rhs = new ArrayList<>();
        
        parseTable.put(new Prediction(NonTerminal.CLASSDECLT,Terminal.SEMI), rhs, ruleNumber++);
        
        //ClassdeclT = Classmom Interfaces BBclassitems
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASSMOM, NonTerminal.INTERFACES, NonTerminal.BBCLASSITEMS);
        parseTable.put(new Prediction(NonTerminal.CLASSDECLT,Terminal.COMMA), rhs, ruleNumber++);
    
        //BBClassitems = brace1 Classitems brace2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACE1, NonTerminal.CLASSITEMS, Terminal.BRACE2);
        parseTable.put(new Prediction(NonTerminal.BBCLASSITEMS,Terminal.BRACE1), rhs, ruleNumber++);
    
        //Classmom = colon Classid
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.COLON, NonTerminal.CLASSID);
        parseTable.put(new Prediction(NonTerminal.CLASSMOM,Terminal.COLON), rhs, ruleNumber++);
        
        //Classmom = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(parseTable, NonTerminal.CLASSMOM, rhs, ruleNumber++, Terminal.BRACE1, Terminal.SEMI);
    
        //Classitems = Classgroup Classitems
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASSGROUP, NonTerminal.CLASSITEMS);
        fillMultipleCells(parseTable, NonTerminal.CLASSITEMS, rhs, ruleNumber++, Terminal.INT, Terminal.FLOAT, Terminal.STRING,
                Terminal.ID, Terminal.KCLASS, Terminal.COLON, Terminal.KFCN);
        
        //Classitems = eps
        rhs = new ArrayList<>();
        
        parseTable.put(new Prediction(NonTerminal.CLASSITEMS,Terminal.BRACE2), rhs, ruleNumber++);
    
        //Classgroup = Class_ctrl
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASS_CTRL);
        parseTable.put(new Prediction(NonTerminal.CLASSGROUP,Terminal.COLON), rhs, ruleNumber++);
        
        //Classgroup = Varlist
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARLIST);
        fillMultipleCells(parseTable, NonTerminal.CLASSGROUP, rhs, ruleNumber++, Terminal.INT, Terminal.FLOAT, Terminal.STRING,
                Terminal.ID, Terminal.KCLASS);
        
        //Classgroup = Mddecls
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.MDDECLS);
        parseTable.put(new Prediction(NonTerminal.CLASSGROUP,Terminal.KFCN), rhs, ruleNumber++);
    
        //Class_ctrl = colon id // in {public, protected, private}
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.COLON, Terminal.ID);
        parseTable.put(new Prediction(NonTerminal.CLASS_CTRL,Terminal.COLON), rhs, ruleNumber++);
        
        //Interfaces = colon Classid Interfaces
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.COLON, NonTerminal.CLASSID, NonTerminal.INTERFACES);
        parseTable.put(new Prediction(NonTerminal.INTERFACES,Terminal.COLON), rhs, ruleNumber++);
        
        //Interfaces = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(parseTable, NonTerminal.INTERFACES, rhs, ruleNumber++, Terminal.BRACE1, Terminal.SEMI);
        
        //Mddecls = Mdheader Mddecls
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.MDHEADER, NonTerminal.MDDECLS);
        parseTable.put(new Prediction(NonTerminal.MDDECLS,Terminal.KIF), rhs, ruleNumber++);
        
        //Mddecls = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(parseTable, NonTerminal.MDDECLS, rhs, ruleNumber++, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.KCLASS, Terminal.COLON);
        
        //Mdheader = kwdfcn Md_id PParmlist Retkind
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KFCN, NonTerminal.MD_ID, NonTerminal.PPARMLIST, NonTerminal.RETKIND);
        parseTable.put(new Prediction(NonTerminal.MDHEADER,Terminal.KFCN), rhs, ruleNumber++);
        
        //Md_id = Classid colon Fcnid
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.CLASSID, Terminal.COLON, NonTerminal.FCNID);
        parseTable.put(new Prediction(NonTerminal.MD_ID,Terminal.ID), rhs, ruleNumber++);
        
        //Fcndefs = Fcndef Fcndefs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.FCNDEF, NonTerminal.FCNDEFS);
        parseTable.put(new Prediction(NonTerminal.FCNDEFS,Terminal.KFCN), rhs, ruleNumber++);
        
        //Fcndefs = eps
        rhs = new ArrayList<>();
        
        parseTable.put(new Prediction(NonTerminal.FCNDEFS,Terminal.KMAIN), rhs, ruleNumber++);
        
        //Fcndef = Fcnheader BBlock
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.FCNHEADER, NonTerminal.BBLOCK);
        parseTable.put(new Prediction(NonTerminal.FCNDEF,Terminal.KFCN), rhs, ruleNumber++);
        
        //Fcnheader = kwdfcn Fcnid PParmlist Retkind
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KFCN, NonTerminal.FCNID, NonTerminal.PPARMLIST, NonTerminal.RETKIND);
        parseTable.put(new Prediction(NonTerminal.FCNHEADER,Terminal.KFCN), rhs, ruleNumber++);
        
        //Fcnid = id
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID);
        parseTable.put(new Prediction(NonTerminal.FCNID,Terminal.ID), rhs, ruleNumber++);
        
        //Retkind = Simplekind
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.SIMPLEKIND);
        fillMultipleCells(parseTable, NonTerminal.RETKIND, rhs, ruleNumber++, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID);
        
        //PParmlist = parens1 PParmlistT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS1, NonTerminal.PPARMLISTT);
        parseTable.put(new Prediction(NonTerminal.PPARMLIST,Terminal.PARENS1), rhs, ruleNumber++);
        
        //PParmlistT = Varspecs parens2
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARSPECS, Terminal.PARENS2);
        fillMultipleCells(parseTable, NonTerminal.PPARMLISTT, rhs, ruleNumber++, Terminal.ID, Terminal.ASTER);
        
        //PParmlistT = parens2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS2);
        parseTable.put(new Prediction(NonTerminal.PPARMLISTT,Terminal.PARENS2), rhs, ruleNumber++);
        
        //Varspecs = Varspec More_varspecs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.VARSPEC, NonTerminal.MORE_VARSPECS);
        fillMultipleCells(parseTable, NonTerminal.VARSPECS, rhs, ruleNumber++, Terminal.ID, Terminal.ASTER);
        
        //More_varspecs = comma Varspecs
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.COMMA, NonTerminal.VARSPECS);
        parseTable.put(new Prediction(NonTerminal.MORE_VARSPECS,Terminal.COMMA), rhs, ruleNumber++);
        
        //More_varspecs = eps
        rhs = new ArrayList<>();
        
        parseTable.put(new Prediction(NonTerminal.MORE_VARSPECS,Terminal.PARENS2), rhs, ruleNumber++);
        
        //PPonly = parens1 parens2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS1, Terminal.PARENS2);
        parseTable.put(new Prediction(NonTerminal.PPONLY,Terminal.PARENS1), rhs, ruleNumber++);
        
        //Stmts = Stmt semi Stmts
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.STMT, Terminal.SEMI, NonTerminal.STMTS);
        fillMultipleCells(parseTable, NonTerminal.STMTS, rhs, ruleNumber++, Terminal.ID, Terminal.ASTER, Terminal.KIF,
                Terminal.KWHILE, Terminal.KPRINT, Terminal.KRETURN);
        
        //Stmts = eps
        rhs = new ArrayList<>();
        
        parseTable.put(new Prediction(NonTerminal.STMTS,Terminal.BRACE2), rhs, ruleNumber++);
        
        //Stmt = Stif
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.STIF);
        parseTable.put(new Prediction(NonTerminal.STMT,Terminal.KIF), rhs, ruleNumber++);
        
        //Stmt = Stwhile
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.STWHILE);
        parseTable.put(new Prediction(NonTerminal.STMT,Terminal.KWHILE), rhs, ruleNumber++);
        
        //Stmt = Stprint
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.STPRINT);
        parseTable.put(new Prediction(NonTerminal.STMT,Terminal.KPRINT), rhs, ruleNumber++);
        
        //Stmt = Strtn
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.STRTN);
        parseTable.put(new Prediction(NonTerminal.STMT,Terminal.KRETURN), rhs, ruleNumber++);
        
        //Stmt = Deref_id equal Expr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.DEREF_ID, Terminal.EQUAL, NonTerminal.EXPR);
        parseTable.put(new Prediction(NonTerminal.STMT,Terminal.ASTER), rhs, ruleNumber++);
        
        //Stmt = id StmtT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID, NonTerminal.STMTT);
        parseTable.put(new Prediction(NonTerminal.STMT,Terminal.ID), rhs, ruleNumber++);
        
        //StmtT = LvalT StmtT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.EQUAL, NonTerminal.LVALT, NonTerminal.STMTT);
        parseTable.put(new Prediction(NonTerminal.STMTT,Terminal.EQUAL), rhs, ruleNumber++);
        
        //StmtT = PPexprs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.PPEXPRS);
        parseTable.put(new Prediction(NonTerminal.STMTT,Terminal.PARENS1), rhs, ruleNumber++);
        
//        //Stasgn = Lval equal Expr
//        rhs = new ArrayList<>();
//        fillRule(rhs, NonTerminal.LVAL, Terminal.EQUAL, NonTerminal.EXPR);
//        fillMultipleCells(predictionTable, NonTerminal.STASGN, rhs, ruleNumber++, Terminal.ID, Terminal.ASTER);
        
        //Lval = id LvalT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID, NonTerminal.LVALT);
        parseTable.put(new Prediction(NonTerminal.LVAL,Terminal.ID), rhs, ruleNumber++);
        
        //Lval = Deref_id
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.DEREF_ID);
        parseTable.put(new Prediction(NonTerminal.LVAL,Terminal.ASTER), rhs, ruleNumber++);
        
        //LvalT = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(parseTable, NonTerminal.LVALT, rhs, ruleNumber++, Terminal.EQUAL, Terminal.SEMI);
        
        //LvalT = KKexpr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.KKEXPR);
        parseTable.put(new Prediction(NonTerminal.LVALT,Terminal.BRACKET1), rhs, ruleNumber++);
        
        //KKexpr = bracket1 Expr bracket2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.BRACKET1, NonTerminal.EXPR, Terminal.BRACKET2);
        parseTable.put(new Prediction(NonTerminal.KKEXPR,Terminal.BRACKET1), rhs, ruleNumber++);
        
//        //Fcall = Fcnid PPexprs
//        rhs = new ArrayList<>();
//        fillRule(rhs, NonTerminal.FCNID, NonTerminal.PPEXPRS);
//        parseTable.put(new Prediction(NonTerminal.FCALL,Terminal.ID), rhs, ruleNumber++);
        
        //PPexprs = parens1 PPexprsT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS1, NonTerminal.PPEXPRST);
        parseTable.put(new Prediction(NonTerminal.PPEXPRS,Terminal.PARENS1), rhs, ruleNumber++);
        
        //PPexprsT = Exprlist parens2
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.EXPRLIST, Terminal.PARENS2);
        fillMultipleCells(parseTable, NonTerminal.PPEXPRST, rhs, ruleNumber++, Terminal.PARENS1, Terminal.ID,
                Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.ASTER, Terminal.AMPERSAND, Terminal.OPEQ,
                Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS,
                Terminal.SLASH, Terminal.CARET);
        
        //PPexprsT = parens2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS2);
        parseTable.put(new Prediction(NonTerminal.PPEXPRST,Terminal.PARENS2), rhs, ruleNumber++);
    
        //Stif = kwdif PPexpr BBlock Elsepart
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KIF, NonTerminal.PPEXPR, NonTerminal.BBLOCK, NonTerminal.ELSEPART);
        parseTable.put(new Prediction(NonTerminal.STIF,Terminal.KIF), rhs, ruleNumber++);
        
        //Elsepart = kwdelseif PPexpr BBlock Elsepart
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KELSEIF, NonTerminal.PPEXPR, NonTerminal.BBLOCK, NonTerminal.ELSEPART);
        parseTable.put(new Prediction(NonTerminal.ELSEPART,Terminal.KELSEIF), rhs, ruleNumber++);
        
        //Elsepart = kwdelse BBlock
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KELSE, NonTerminal.BBLOCK);
        parseTable.put(new Prediction(NonTerminal.ELSEPART,Terminal.KELSE), rhs, ruleNumber++);
        
        //Elsepart = eps
        rhs = new ArrayList<>();
        
        parseTable.put(new Prediction(NonTerminal.ELSEPART,Terminal.SEMI), rhs, ruleNumber++);
        
        //Stwhile = kwdwhile PPexpr BBlock
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KWHILE, NonTerminal.PPEXPR, NonTerminal.BBLOCK);
        parseTable.put(new Prediction(NonTerminal.STWHILE,Terminal.KWHILE), rhs, ruleNumber++);
        
        //Stprint = kprint PPexprs
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KPRINT, NonTerminal.PPEXPRS);
        parseTable.put(new Prediction(NonTerminal.STPRINT,Terminal.KPRINT), rhs, ruleNumber++);
        
        //Strtn = kwdreturn StrtnT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.KRETURN, NonTerminal.STRTNT);
        parseTable.put(new Prediction(NonTerminal.STRTN,Terminal.KRETURN), rhs, ruleNumber++);
        
        //StrtnT = Expr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.EXPR);
        fillMultipleCells(parseTable, NonTerminal.STRTNT, rhs, ruleNumber++, Terminal.PARENS1, Terminal.ID,
                Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.ASTER, Terminal.AMPERSAND, Terminal.OPEQ,
                Terminal.OPLE, Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS,
                Terminal.SLASH, Terminal.CARET);
        
        //StrtnT = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(parseTable, NonTerminal.STRTNT, rhs, ruleNumber++, Terminal.SEMI);
        
        //PPexpr = parens1 Expr parens2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PARENS1, NonTerminal.EXPR, Terminal.PARENS2);
        parseTable.put(new Prediction(NonTerminal.PPEXPR,Terminal.PARENS1), rhs, ruleNumber++);
        
        //Expr = Oprel Rterm Expr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.OPREL, NonTerminal.RTERM, NonTerminal.EXPR);
        fillMultipleCells(parseTable, NonTerminal.EXPR, rhs, ruleNumber++, Terminal.AMPERSAND, Terminal.OPEQ, Terminal.OPLE,
                Terminal.OPGE, Terminal.ANGLE1, Terminal.ANGLE2);
        
        //Expr = Rterm Expr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.RTERM, NonTerminal.EXPR);
        fillMultipleCells(parseTable, NonTerminal.EXPR, rhs, ruleNumber++, Terminal.PLUS, Terminal.MINUS, Terminal.SLASH,
                Terminal.CARET, Terminal.ID, Terminal.INT, Terminal.FLOAT, Terminal.STRING, Terminal.PARENS1);
        
        //Expr = eps
        rhs = new ArrayList<>();
        
        fillMultipleCells(parseTable, NonTerminal.EXPR, rhs, ruleNumber++, Terminal.BRACE2, Terminal.PARENS2, Terminal.SEMI,
                Terminal.BRACKET2, Terminal.COMMA);
        
        //Rterm = Opadd Term Rterm
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.OPADD, NonTerminal.TERM, NonTerminal.RTERM);
        fillMultipleCells(parseTable, NonTerminal.RTERM, rhs, ruleNumber++, Terminal.PLUS, Terminal.MINUS);
        
        //Rterm = Term Rterm
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.TERM, NonTerminal.RTERM);
        fillMultipleCells(parseTable, NonTerminal.RTERM, rhs, ruleNumber++, Terminal.PARENS1, Terminal.INT, Terminal.ASTER,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.AMPERSAND, Terminal.SLASH, Terminal.CARET);
        
        //Rterm = eps
        rhs = new ArrayList<>();
    
        fillMultipleCells(parseTable, NonTerminal.RTERM, rhs, ruleNumber++, Terminal.BRACE2, Terminal.PARENS2, Terminal.SEMI,
                Terminal.BRACKET2, Terminal.COMMA, Terminal.OPEQ, Terminal.OPNE, Terminal.OPLE, Terminal.OPGE,
                Terminal.ANGLE1, Terminal.ANGLE2);
        
        //Term = Opmul Factcheck Term
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.OPMUL, NonTerminal.FACTCHECK, NonTerminal.TERM);
        fillMultipleCells(parseTable, NonTerminal.TERM, rhs, ruleNumber++, Terminal.ASTER, Terminal.SLASH, Terminal.CARET);
        
        //Term = Fact Term
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.FACT, NonTerminal.TERM);
        fillMultipleCells(parseTable, NonTerminal.TERM, rhs, ruleNumber++, Terminal.PARENS1, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.AMPERSAND);
        
        //Term = eps
        rhs = new ArrayList<>();
    
        fillMultipleCells(parseTable, NonTerminal.TERM, rhs, ruleNumber++, Terminal.BRACE2, Terminal.PARENS2, Terminal.SEMI,
                Terminal.BRACKET2, Terminal.COMMA, Terminal.OPEQ, Terminal.OPNE, Terminal.OPLE, Terminal.OPGE,
                Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS, Terminal.SLASH, Terminal.CARET);
        
        //Factcheck = Fact
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.FACT);
        fillMultipleCells(parseTable, NonTerminal.FACTCHECK, rhs, ruleNumber++, Terminal.PARENS1, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING, Terminal.ID, Terminal.AMPERSAND);
        
        //Factcheck = Deref_id
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.DEREF_ID);
        parseTable.put(new Prediction(NonTerminal.FACTCHECK,Terminal.ASTER), rhs, ruleNumber++);
        
        //Fact = Basekind
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.BASEKIND);
        fillMultipleCells(parseTable, NonTerminal.FACT, rhs, ruleNumber++, Terminal.INT,
                Terminal.FLOAT, Terminal.STRING);
        
        //Fact = Addrof_id
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.ADDROF_ID);
        parseTable.put(new Prediction(NonTerminal.FACT,Terminal.AMPERSAND), rhs, ruleNumber++);
        
        //Fact = PPexpr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.PPEXPR);
        parseTable.put(new Prediction(NonTerminal.FACT,Terminal.PARENS1), rhs, ruleNumber++);
        
        //Fact = id FactT
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ID, NonTerminal.FACTT);
        parseTable.put(new Prediction(NonTerminal.FACT,Terminal.ID), rhs, ruleNumber++);
        
        //FactT = PPexprs
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.PPEXPRS);
        parseTable.put(new Prediction(NonTerminal.FACTT,Terminal.PARENS1), rhs, ruleNumber++);
        
        //FactT = KKexpr
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.KKEXPR);
        parseTable.put(new Prediction(NonTerminal.FACTT,Terminal.BRACKET1), rhs, ruleNumber++);
        
        //FactT = eps
        rhs = new ArrayList<>();
    
        fillMultipleCells(parseTable, NonTerminal.FACTT, rhs, ruleNumber++, Terminal.BRACE2, Terminal.PARENS2, Terminal.SEMI,
                Terminal.BRACKET2, Terminal.COMMA, Terminal.AMPERSAND, Terminal.OPEQ, Terminal.OPNE, Terminal.OPLE,
                Terminal.OPGE, Terminal.ASTER, Terminal.INT, Terminal.FLOAT, Terminal.ID, Terminal.STRING,
                Terminal.ANGLE1, Terminal.ANGLE2, Terminal.PLUS, Terminal.MINUS, Terminal.SLASH, Terminal.CARET);
        
        //Addrof_id = ampersand id
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.AMPERSAND, Terminal.ID);
        parseTable.put(new Prediction(NonTerminal.ADDROF_ID,Terminal.AMPERSAND), rhs, ruleNumber++);
        
        //Oprel = opeq
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.OPEQ);
        parseTable.put(new Prediction(NonTerminal.OPREL,Terminal.OPEQ), rhs, ruleNumber++);
        
        //Oprel = opne
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.OPNE);
        parseTable.put(new Prediction(NonTerminal.OPREL,Terminal.OPNE), rhs, ruleNumber++);
        
        //Oprel = Lthan
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.LTHAN);
        parseTable.put(new Prediction(NonTerminal.OPREL,Terminal.ANGLE1), rhs, ruleNumber++);
        
        //Oprel = ople
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.OPLE);
        parseTable.put(new Prediction(NonTerminal.OPREL,Terminal.OPLE), rhs, ruleNumber++);
        
        //Oprel = opge
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.OPGE);
        parseTable.put(new Prediction(NonTerminal.OPREL,Terminal.OPGE), rhs, ruleNumber++);
        
        //Oprel = Gthan
        rhs = new ArrayList<>();
        fillRule(rhs, NonTerminal.GTHAN);
        parseTable.put(new Prediction(NonTerminal.OPREL,Terminal.ANGLE2), rhs, ruleNumber++);
        
        //Lthan = angle1
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ANGLE1);
        parseTable.put(new Prediction(NonTerminal.LTHAN,Terminal.ANGLE1), rhs, ruleNumber++);
        
        //Gthan = angle2
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ANGLE2);
        parseTable.put(new Prediction(NonTerminal.GTHAN,Terminal.ANGLE2), rhs, ruleNumber++);
        
        //Opadd = plus
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.PLUS);
        parseTable.put(new Prediction(NonTerminal.OPADD,Terminal.PLUS), rhs, ruleNumber++);
        
        //Opadd = minus
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.MINUS);
        parseTable.put(new Prediction(NonTerminal.OPADD,Terminal.MINUS), rhs, ruleNumber++);
        
        //Opmul = aster
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.ASTER);
        parseTable.put(new Prediction(NonTerminal.OPMUL,Terminal.ASTER), rhs, ruleNumber++);
        
        //Opmul = slash
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.SLASH);
        parseTable.put(new Prediction(NonTerminal.OPMUL,Terminal.SLASH), rhs, ruleNumber++);
        
        //Opmul = caret
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.CARET);
        parseTable.put(new Prediction(NonTerminal.OPMUL,Terminal.CARET), rhs, ruleNumber++);
        
        //StmtT = equal Expr
        rhs = new ArrayList<>();
        fillRule(rhs, Terminal.EQUAL, NonTerminal.EXPR);
        parseTable.put(new Prediction(NonTerminal.STMTT, Terminal.EQUAL), rhs, ruleNumber++);
        
    }
}
