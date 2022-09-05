package JavaCLStuff;

final class CurrentFrame extends Frame {

CurrentFrame(final Label owner) {
 super(owner);
}


@Override
void execute(
   final int opcode, final int arg, final Symbol symbolArg, final SymbolTable symbolTable) {
 super.execute(opcode, arg, symbolArg, symbolTable);
 Frame successor = new Frame(null);
 merge(symbolTable, successor, 0);
 copyFrom(successor);
}
}

