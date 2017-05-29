require 'io/console'

# Reads keypresses from the user including 2 and 3 escape character sequences.
def read_char
  STDIN.echo = false
  STDIN.raw!

  begin
    input = STDIN.read_nonblock(1)
    if input == "\e"
      input << STDIN.read_nonblock(3) rescue nil
      input << STDIN.read_nonblock(2) rescue nil
    end
  rescue
    retry
  end
ensure
  STDIN.echo = true
  STDIN.cooked!

  return input
end

def read_char2
  begin
    # See if a 'Q' has been typed yet
    while c = STDIN.read_nonblock(1)
      if c == "\e"
        binding.pry
        c << STDIN.read_nonblock(3) rescue nil
        c << STDIN.read_nonblock(2) rescue nil
      end
      return c
    end
    # No 'Q' found
  rescue Errno::EINTR
    c
  rescue Errno::EAGAIN
    c
  rescue EOFError
    c
  end
  c
end
