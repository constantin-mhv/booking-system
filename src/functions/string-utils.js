export function humanReadable(s) {
    return s.replace("_", " ").toLowerCase().replace(/\b(\w)/g, s => s.toUpperCase());
}