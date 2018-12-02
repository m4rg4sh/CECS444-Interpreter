package Symbols;

/**
 * This class represents the Symbols in the Grammar (Terminals and Non-Terminals)
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public interface Symbol {
    int getId();
    boolean createsScope();
}
